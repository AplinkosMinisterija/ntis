package lt.project.ntis.logic.forms.brokerws;

import java.math.BigInteger;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.scheduler.ExecutorJob;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.project.ntis.dao.NtisBuildingNtrOwnersDAO;
import lt.project.ntis.enums.NtisBuildingOwnerType;
import lt.project.ntis.models.NtrOwnerModel;

/**
 * Pagrindinė integracijos su Registrų Centro BrokerWS sistema klasė.
 */
@Component
public class RcBroker {

    record Problem(String message, Exception exception) {

    }

    private static final Logger log = LoggerFactory.getLogger(RcBroker.class);

    static final String MDC_ORG_ID = "ntis:orgId";

    static final String MDC_ORG_CODE = "ntis:orgCode";

    static final String MDC_PER_ID = "ntis:perId";

    static final String MDC_PER_CODE = "ntis:perCode";

    static final String MDC_REG_ID = "ntis:regNr";

    /**
     * Flag in spark.spr_properties table identifying if NTR data updates must be performed (Y:immediately; N:using scheduler);
     */
    private static final String NTR_UPDATE_URGENCY_FLAG = "RcBrokerCallsFor-UpdateNtrOwnerData-IMMEDIATE";

    @Autowired
    RcBrokerNtrDataUpdateTaskRequest rcBrokerNtrDataUpdateTaskRequest;

    @Autowired
    ExecutorJob executerJob;

    @Autowired
    RcBrokerWs brokerWs;

    @Autowired
    RcBrokerDb brokerDb;

    @Autowired
    RcBrokerJaxb brokerJaxb;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Value("${brokerws.active}")
    private boolean active = true;

    /**
     * Patikrina, ar Registrų Centro turimi asmens duomenys sutampa su pateiktais.
     * 
     * @param personCode tikrinamas asmens kodas
     * @param personName tikrinamo asmens vardas
     * @param personSurname tikrinamo asmens pavardė
     * @return true jeigu RC turi duomenų pagal nurodytą asmens kodą, ir tie duomenys sutampa su pateiktais; false jeigu duomenų apie tokį asmens kodą RC neturi, arba turimi duomenys nesutampa su pateiktais. 
     */
    public boolean isPersonCodeKnown(String personCode, String personName, String personSurname) throws Exception {
        boolean result = false;
        if (isRcBrokerOn(RcBrokerCheckCode.IS_PERSON_CODE_KNOWN)) {
            try {
                String xmlData = brokerWs.retrieveData605(personCode);
                if (xmlData != null) {
                    Gr605Data data = brokerJaxb.collectResponse605(xmlData);
                    result = personCode.equals(Long.toString(data.getAsmKodas())) && personName.equalsIgnoreCase(data.getAsmVardas())
                            && personSurname.equalsIgnoreCase(data.getAsmPav());
                }
            } catch (Exception e) {
                log.error("GR 605 service call failed.", e);
            }
        } else {
            result = true;
            log.warn("Integration with RC GR is switched off either in ntis-api.properties settings or in spr_properties table.");
        }
        return result;
    }

    void renewJarData(Connection conn, ProblemsConsumer problemsConsumer) throws Exception {
        if (isRcBrokerOn(RcBrokerCheckCode.UPDATE_JAR_DATA)) {
            Collection<Integer> orgCodeList = brokerDb.collectRenewableOrgCodes(conn, problemsConsumer);
            log.info("Collected {} renewable records from DB.", orgCodeList.size());
            Map<Integer, Jar4020Data> updateList = collectRenewData(orgCodeList, problemsConsumer);
            log.info("Collected {} records from RC.", updateList.size());
            applyRenewList(conn, updateList, problemsConsumer);
        } else {
            log.warn("Integration with RC JAR is switched off either in ntis-api.properties settings or in spr_properties table.");
        }
    }

    private Map<Integer, Jar4020Data> collectRenewData(Collection<Integer> orgCodes, ProblemsConsumer problemsConsumer) throws Exception {
        long t1 = System.currentTimeMillis();
        int totalCnt = orgCodes.size();
        int cnt = 0;
        Map<Integer, Jar4020Data> result = new HashMap<>();
        for (Integer objKodas : orgCodes) {
            cnt++;
            try {
                long t2 = System.currentTimeMillis();
                result.put(objKodas, brokerJaxb.collectResponse4020(brokerWs.retrieveData4020(objKodas)));
                long t3 = System.currentTimeMillis();
                log.info("{}/{} call (objKodas {}) to RC completed. Call time {} ms, all calls so far {} ms.", cnt, totalCnt, objKodas, (t3 - t2), (t3 - t1));

            } catch (Exception e) {
                String message = String.format("Failed to retrieve data using 4020 service for obj_kodas %d.", objKodas);
                log.error(message, e);
                problemsConsumer.accept(new Problem(message, e));
                result.put(objKodas, null);
            }
        }
        return result;
    }

    private void applyRenewList(Connection conn, Map<Integer, Jar4020Data> renewList, ProblemsConsumer problemsConsumer) throws Exception {
        for (Map.Entry<Integer, Jar4020Data> data : renewList.entrySet()) {
            try {
                log.info("Updating organization {}.", data.getKey());
                brokerDb.updateOrganization(conn, data.getKey(), data.getValue());

            } catch (Exception e) {
                String message = String.format("Failed to update organisation record in DB for obj_kodas %d.", data.getKey());
                log.error(message, e);
                problemsConsumer.accept(new Problem(message, e));
            }
        }
    }

    void updateJarData(Connection conn, LocalDate date, ProblemsConsumer problemsConsumer) throws Exception {
        if (isRcBrokerOn(RcBrokerCheckCode.UPDATE_JAR_DATA)) {
            for (Jar4020Data data : collectAddData(date, problemsConsumer)) {
                addOrganization(conn, data, problemsConsumer);
            }
            for (Jar4020Data data : collectUpdateData(date, problemsConsumer)) {
                updateOrganization(conn, data, problemsConsumer);
            }
            for (Integer objKodas : collectDeleteData(date, problemsConsumer)) {
                removeOrganization(conn, objKodas, date, problemsConsumer);
            }
        } else {
            log.warn("Integration with RC JAR is switched off either in ntis-api.properties settings or in spr_properties table.");
        }
    }

    private void addOrganization(Connection conn, Jar4020Data data, ProblemsConsumer problemsConsumer) {
        try {
            log.info("Adding organization {}.", data.getObjKodas());
            brokerDb.addOrganization(conn, data);

        } catch (Exception e) {
            String message = String.format("Failed to add organization for objCode %d.", data.getObjKodas());
            log.error(message, e);
            problemsConsumer.accept(new Problem(message, e));
        }
    }

    private void updateOrganization(Connection conn, Jar4020Data data, ProblemsConsumer problemsConsumer) {
        try {
            log.info("Updating organization {}.", data.getObjKodas());
            brokerDb.updateOrganization(conn, data.getObjKodas(), data);

        } catch (Exception e) {
            String message = String.format("Failed to update organization for objCode %d.", data.getObjKodas());
            log.error(message, e);
            problemsConsumer.accept(new Problem(message, e));
        }
    }

    private void removeOrganization(Connection conn, Integer objKodas, LocalDate date, ProblemsConsumer problemsConsumer) {
        try {
            log.info("Removing organization {}.", objKodas);
            brokerDb.removeOrganization(conn, objKodas, Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        } catch (Exception e) {
            String message = String.format("Failed to remove organization for objCode %d.", objKodas);
            log.error(message, e);
            problemsConsumer.accept(new Problem(message, e));
        }
    }

    private Collection<Jar4020Data> collectAddData(LocalDate date, ProblemsConsumer problemsConsumer) throws Exception {
        Collection<Jar4020Data> result = new ArrayList<>();
        try {
            String addData = brokerWs.retrieveData46(date);
            if (addData != null) {
                for (Integer objKodas : brokerJaxb.collectObjkodasResponse46(addData)) {
                    Jar4020Data orgData = retrieveOrganizationData(objKodas, problemsConsumer);
                    if (orgData != null) {
                        result.add(orgData);
                    }
                }
            }
        } catch (Exception e) {
            String message = String.format("Failed to retrieve RC 46 service data (added organizations) for date %s.", date.toString());
            log.error(message, e);
            problemsConsumer.accept(new Problem(message, e));
        }
        return result;
    }

    private Jar4020Data retrieveOrganizationData(Integer objKodas, ProblemsConsumer problemsConsumer) throws Exception {
        Jar4020Data result = null;
        try {
            result = brokerJaxb.collectResponse4020(brokerWs.retrieveData4020(objKodas));

        } catch (Exception e) {
            String message = String.format("Failed to retrieve organization data for objCode %d.", objKodas);
            problemsConsumer.accept(new Problem(message, e));
        }
        return result;
    }

    private Collection<Jar4020Data> collectUpdateData(LocalDate date, ProblemsConsumer problemsConsumer) throws Exception {
        Collection<Jar4020Data> result = new ArrayList<>();
        try {
            String updateData = brokerWs.retrieveData47(date);
            if (updateData != null) {
                for (Integer objKodas : brokerJaxb.collectObjkodasResponse47(updateData)) {
                    Jar4020Data orgData = retrieveOrganizationData(objKodas, problemsConsumer);
                    if (orgData != null) {
                        result.add(orgData);
                    }
                }
            }
        } catch (Exception e) {
            String message = String.format("Failed to retrieve RC 47 service data (updated organizations) for date %s.", date.toString());
            log.error(message, e);
            problemsConsumer.accept(new Problem(message, e));
        }
        return result;
    }

    private Collection<Integer> collectDeleteData(LocalDate date, ProblemsConsumer problemsConsumer) throws Exception {
        Collection<Integer> result = Collections.emptySet();
        try {
            String deleteData = brokerWs.retrieveData48(date);
            if (deleteData != null) {
                result = brokerJaxb.collectObjkodasResponse48(deleteData);
            }
        } catch (Exception e) {
            String message = String.format("Failed to retrieve RC 48 service data (removed organizations) for date %s.", date.toString());
            log.error(message, e);
            problemsConsumer.accept(new Problem(message, e));
        }
        return result;
    }

    /**
     * Gauna savininkų sąrašą pagal registro numerį.
     * 
     * @param bnRegNr registro tarnybos numeris ir registro numeris tarnyboje
     * @return savininkų sąrašas
     * @throws Exception
     */
    public List<NtrOwnerModel> retrieveNtrOwnersData(String bnRegNr) throws Exception {
        List<NtrOwnerModel> result = Collections.emptyList();
        if (isRcBrokerOn(RcBrokerCheckCode.RETRIEVE_NTR_OWNERS_DATA)) {
            Map<String, String> mdc = MDC.getCopyOfContextMap();
            try {
                MDC.put(MDC_REG_ID, bnRegNr);
                log.info("START BrokerWS call for NTR owneres data for regNr:{}.", MDC.get(MDC_REG_ID));

                String[] tokens = bnRegNr.split("/");
                BigInteger regTarnNr = new BigInteger(tokens[0]);
                BigInteger regNr = new BigInteger(tokens[1]);
                result = RcBrokerJaxb.toNtrOwnerData(brokerJaxb.toRegistras(brokerWs.retrieveData95Optional(regTarnNr, regNr)));

            } catch (SparkBusinessException e) {
                throw e;
            } catch (Exception e) {
                throw new Exception("NTR owners data retrieval by regNr from BrokerWS failed.", e);
            } finally {
                log.info("FINISH BrokerWS call for NTR owners data for regNr:{}.", MDC.get(MDC_REG_ID));
                MDC.setContextMap(mdc);
            }
        } else {
            log.warn("Integration with RC NTR is switched off either in ntis-api.properties settings or in spr_properties table.");
        }
        return result;
    }

    /**
     * Priklausomai nuo {@value #NTR_UPDATE_URGENCY_FLAG} reikšmės, sukuria užsakymą atnaujinti asmens duomenis iš NTR, arba įvykdo atnaujinimą iš karto.
     * @param conn - prisijungimas prie DB.
     * @param perId - asmens ID.
     */
    public void startPersonNtrDataRequest(Connection conn, Double perId) throws Exception {
        if (isImmediateNtrOwnerDataUpdateOn()) {
            Map<String, String> params = Collections.singletonMap(RcBrokerNtrDataUpdateTaskRequest.JOB_REQUEST_PARAM_PERSON_ID, perId.toString());
            Double requestId = rcBrokerNtrDataUpdateTaskRequest.createJobRequest(conn, null, Languages.LT, params);
            conn.commit();
            executerJob.execute(requestId);
        } else {
            brokerDb.startPersonNtrDataRequest(conn, perId);
        }
    }

    /**
     * Priklausomai nuo {@value #NTR_UPDATE_URGENCY_FLAG} reikšmės, sukuria užsakymą atnaujinti organizacijos duomenis iš NTR, arba įvykdo atnaujinimą iš karto.
     * @param conn - prisijungimas prie DB.
     * @param orgId - sesijos organizacijos ID.
     */
    public void startOrganizationNtrDataRequest(Connection conn, Double orgId) throws Exception {
        if (isImmediateNtrOwnerDataUpdateOn()) {
            Map<String, String> params = Collections.singletonMap(RcBrokerNtrDataUpdateTaskRequest.JOB_REQUEST_PARAM_ORGANISATION_ID, orgId.toString());
            Double requestId = rcBrokerNtrDataUpdateTaskRequest.createJobRequest(conn, null, Languages.LT, params);
            conn.commit();
            executerJob.execute(requestId);
        } else {
            brokerDb.startOrganizationNtrDataRequest(conn, orgId);
        }
    }

    void updateNtrOwnerData(Connection conn, Double perId, Double orgId) throws Exception {
        if (isRcBrokerOn(RcBrokerCheckCode.UPDATE_NTR_OWNER_DATA)) {
            Map<String, String> mdc = MDC.getCopyOfContextMap();
            try {
                MDC.put(MDC_ORG_ID, String.valueOf(orgId));
                MDC.put(MDC_PER_ID, String.valueOf(perId));
                log.info("START BrokerWS call for NTR data for perId:{}; orgId:{}.", MDC.get(MDC_PER_ID), MDC.get(MDC_ORG_ID));

                doUpdate(conn, perId, orgId);

            } catch (Exception e) {
                throw new Exception("NTR owner data update from BrokerWS failed.", e);

            } finally {
                log.info("FINISH BrokerWS call for NTR data for perId:{}; perCode:{}; orgId:{}; orgCode:{}.", MDC.get(MDC_PER_ID), MDC.get(MDC_PER_CODE),
                        MDC.get(MDC_ORG_ID), MDC.get(MDC_ORG_CODE));
                MDC.setContextMap(mdc);
            }
        } else {
            log.warn("Integration with RC NTR is switched off either in ntis-api.properties settings or in spr_properties table.");
        }
    }

    private void doUpdate(Connection conn, Double perId, Double orgId) throws Exception {
        NtisBuildingNtrOwnersDAO ownerInfo = new NtisBuildingNtrOwnersDAO();

        Boolean ownerLegalPerson = Boolean.FALSE;
        if (orgId != null) {
            SprOrganizationsDAO organization = brokerDb.loadOrganization(conn, orgId);
            ownerLegalPerson = Boolean.TRUE;

            ownerInfo.setBno_type(NtisBuildingOwnerType.J.getCode());
            ownerInfo.setBno_code(organization.getOrg_code());
            ownerInfo.setBno_org_name(organization.getOrg_name());

            MDC.put(MDC_ORG_CODE, ownerInfo.getBno_code());
        } else {
            SprPersonsDAO person = brokerDb.loadPerson(conn, perId);

            ownerInfo.setBno_type(NtisBuildingOwnerType.F.getCode());
            ownerInfo.setBno_code(person.getPer_code());
            ownerInfo.setBno_name(person.getPer_name());
            ownerInfo.setBno_lastname(person.getPer_surname());

            MDC.put(MDC_PER_CODE, ownerInfo.getBno_code());
        }

        Set<Long> objInvCodes = collectObjInvCodes(ownerLegalPerson.booleanValue(), ownerInfo.getBno_code(), ownerInfo.getBno_lastname());
        if (!objInvCodes.isEmpty()) {
            brokerDb.createAndFillTempTable(conn, objInvCodes);
            brokerDb.deleteObsoleteFacilityOwners(conn, perId, orgId);
            brokerDb.createNewFacilityOwners(conn, perId, orgId);

            String ownerCode = ownerInfo.getBno_code();
            if (ownerCode != null && !ownerCode.isBlank()) {
                brokerDb.deleteObsoleteBuildingOwners(conn, ownerInfo.getBno_code());
                brokerDb.createNewBuildingOwners(conn, ownerInfo);
            }
        }
    }

    private Set<Long> collectObjInvCodes(boolean asmTypeLegal, String asmk, String asmp) throws Exception {
        Set<Long> result = new HashSet<>();
        List<Ntr40Data> registrai = RcBrokerJaxb.toNtr40Data(brokerJaxb.toAsmensRegistrai(brokerWs.retrieveData40(asmTypeLegal, asmk, asmp)));
        if (!registrai.isEmpty()) {
            List<String> ntr95dataList = new ArrayList<>();
            for (Ntr40Data ntr40Data : registrai) {
                ntr95dataList.add(brokerWs.retrieveData95(ntr40Data.getRegTarnNr(), ntr40Data.getRegNr()));
            }
            for (lt.project.ntis.brokerws.ntr95.REGISTRAS registras : brokerJaxb.toRegistrasList(ntr95dataList)) {
                result.addAll(RcBrokerJaxb.toObjInvCodes(registras));
            }
        }
        return result;
    }

    private boolean isImmediateNtrOwnerDataUpdateOn() throws Exception {
        return YesNo.getEnumByCode(dbPropertyManager.getPropertyByName(NTR_UPDATE_URGENCY_FLAG, YesNo.N.getCode())).getBoolean();
    }

    private boolean isRcBrokerOn(RcBrokerCheckCode rcBrokerCheckCode) throws Exception {
        return active && YesNo.getEnumByCode(dbPropertyManager.getPropertyByName(rcBrokerCheckCode.getSprPropertiesPrnName(), YesNo.Y.getCode())).getBoolean();
    }

    private enum RcBrokerCheckCode {

        /**
         * Uses service GR 605 - Penktas duomenų sąrašas XML formatu.
         */
        IS_PERSON_CODE_KNOWN("RcBrokerCallsFor-IsPersonCodeKnown-ENABLED"),

        /**
         * Uses services:
         * JAR 46 - JA naujai įregistruotų objektų sąrašas XML formatu, 
         * JAR 47 - JA įregistruotų objektų, kurių duomenys keitėsi, sąrašas XML formatu, 
         * JAR 48 - JA išregistruotų objektų sąrašas XML formatu, 
         * JAR 4020 - Trumpasis (identifikacinių duomenų, išskyrus vadovo) išrašas, XML formatu.
         */
        UPDATE_JAR_DATA("RcBrokerCallsFor-UpdateJarData-ENABLED"),

        /**
         * Uses service NTR 95 - išrašas (ieškoma pagal nekilnojamojo turto registro numerį).
         */
        RETRIEVE_NTR_OWNERS_DATA("RcBrokerCallsFor-RetrieveNtrOwnersData-ENABLED"),

        /**
         * Uses services:
         * NTR 40 - NT registrų paieška pagal asmenį, 
         * NTR 95 - išrašas (ieškoma pagal nekilnojamojo turto registro numerį).
         */
        UPDATE_NTR_OWNER_DATA("RcBrokerCallsFor-UpdateNtrOwnerData-ENABLED");

        private RcBrokerCheckCode(String prpName) {
            this.sprPropertiesPrpName = prpName;
        }

        /**
         * Value of prp_name column in spark.spr_properties db table record. 
         */
        private String sprPropertiesPrpName;

        public String getSprPropertiesPrnName() {
            return sprPropertiesPrpName;
        }
    }

}
