package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.dao.NtisFacilityOwnersDAO;
import lt.project.ntis.enums.NtisFacilityOwnerType;
import lt.project.ntis.logic.forms.brokerws.RcBroker;
import lt.project.ntis.models.NtisFacilityManagerEditModel;
import lt.project.ntis.models.NtisWastewaterTreatmentFacility;
import lt.project.ntis.service.NtisFacilityOwnersDBService;

/**
 * Klasė skirta formos "Nuotekų tvarkymo įrenginio valdytojai" (formos kodas N4080)  biznio logikai apibrėžti
 */
@Component
public class NtisWfManagersEdit extends FormBase {

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    NtisFacilityOwnersDBService facilityOwnersDbService;

    @Autowired
    SprPersonsDBService personsDbService;

    @Autowired
    RcBroker rcBroker;

    @Autowired
    DBStatementManager dbStatementManager;

    @Override
    public String getFormName() {
        return "NTIS_WF_MANAGERS_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Pridėti nuotekų tvarkymo įrenginio valdytoją", "Nuotekų tvarkymo įrenginio valdytojo pridėjimo forma");
        addFormActions(FormBase.ACTION_CREATE);
    }

    /**
     * Pagal perduodamą įrašo id, metodas grąžins pasirinkto nuotekų tvarkymo įrenginio duomenis
     * @param conn - prisijungimas prie DB
     * @param wtfId - nuotekų tvarkymo įrenginio įrašo NTIS_WASTEWATER_TREATMENT_FACI lentelėje id
     * @param orgId - naudotojo organizacijios id, kuriai priklauso dirbantis naudotojas
     * @param perId - nuoroda į asmens informaciją, kuriai yra priskirtas prisijungęs naudotojas.
     * @return NtisWastewaterTreatmentFacility objektas
     * @throws Exception
     */
    public NtisWastewaterTreatmentFacility getSelectedWtf(Connection conn, Double wtfId, Double orgId, Double perId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisWfManagersEdit.ACTION_CREATE);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("select wtf.wtf_id, " + //
                "coalesce (wav.full_address_text, " + //
                "        '('||wtf.wtf_facility_latitude || ', ' || wtf.wtf_facility_longitude || ')') as wtf_address " + //
                "from ntis.ntis_wastewater_treatment_faci wtf " + //
                "inner join ntis.ntis_facility_owners fo on fo.fo_wtf_id = wtf.wtf_id " + //
                "left join ntis.ntis_address_vw wav on wav.address_id = wtf.wtf_ad_id " + //
                "left join (select ful_id, ful_wtf_id " + //
                "from ntis_facility_update_logs " + //
                "where ful_operation = 'INSERT' " + //
                "and ful_usr_id = ?::int and ful_wtf_id = ?::int) ful on ful_wtf_id = wtf.wtf_id " + //
                "where wtf.wtf_id = ?::int and (fo.fo_owner_type = '" + NtisFacilityOwnerType.OWNER + "' or ful.ful_id is not null) ");
        stmt.setWhereExists(true);
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(wtfId);
        stmt.addSelectParam(wtfId);
        if (orgId != null) {
            stmt.addParam4WherePart(" fo.fo_org_id = ?::int ", orgId);
        } else {
            stmt.addParam4WherePart("fo.fo_per_id = ?::int ", perId);
        }
        ArrayList<NtisWastewaterTreatmentFacility> data = (ArrayList<NtisWastewaterTreatmentFacility>) baseControllerJDBC.selectQueryAsObjectArrayList(conn,
                stmt, NtisWastewaterTreatmentFacility.class);
        if (data != null && !data.isEmpty()) {
            return data.get(0);
        } else {
            throw new SparkBusinessException(new S2Message("common.error.notAnOwner", SparkMessageType.ERROR, "User is not the owner of selected facility"));
        }
    }

    /**
     * Metodas sukurs naują nuotekų tvarkymo įrenginio valdytojo (MANAGER) įrašą NTIS_FACILITY_OWNERS lentelėje.
     * Prieš naujo įrašo išsaugojimą bus patikrinama:
     * 1) ar naudotojas yra pasirinkto nuotekų tvarkymo įrenginio savininkas (OWNER); jei naudotojas nėra savininkas, išvedamas klaidos pranešimas.
     * 2) ar spr_persons lentelėje yra sukurtas įrašas su NtisFacilityManagerEditModel objekte perduodamu per_code;
     * 3) jeigu spr_persons lentelėje įrašas su perduodamu per_code egzistuoja,  tikrinama ar pasirinktam nuotekų tvarkymo įrenginiui 
     *    jau yra sukurtas nuotekų tvarkymo įrenginio valdytojo ar savininko įrašas (MANAGER arba OWNER) su šiuo per_code ir ar suteiktos 
     *    teisės vis dar galioja; jeigu toks įrašas su galiojančiomis teisėmis egzistuoja, išvedamas klaidos pranešimas, 
     *    nurodantis, kad toks valdytojas jau priskirtas. Kitu atveju, jeigu ownerRecord yra null, sukuriamas naujas NtisFacilityOwnersDAO objektas, 
     *    jam priskiriami NtisFacilityManagerEditModel objektu perduodami duomenys.
     * 4) Jeigu pradinėje patikroje, kurioje tikrinama, ar egzistuoja SPR_PERSONS įrašas su NtisFacilityManagerEditModel objektu perduodamu per_code,
     *    egzistuojantis įrašas nėra randamas, tuomet sukuriami nauji įrašai tiek SPR_PERSONS, tiek NTIS_FACILITY_OWNERS lentelėse.
     * @param conn - prisijungimas prie DB
     * @param manager - NtisFacilityManagerEditModel objektas
     * @param perId - nuoroda į asmens informaciją, kuriai yra priskirtas prisijungęs naudotojas.
     * @param orgId - naudotojo organizacijios id, kuriai priklauso dirbantis naudotojas
     * @return NtisFacilityManagerEditModel objektas
     * @throws Exception
     */
    public NtisFacilityManagerEditModel saveManager(Connection conn, NtisFacilityManagerEditModel manager, Double perId, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_CREATE);
        if (orgId != null) {
            if (Boolean.FALSE.equals(facilityOwnersDbService.isOrganizationAnOwnerOfFacility(conn, orgId, manager.getFo_wtf_id()))) {
                throw new SparkBusinessException(
                        new S2Message("common.error.notAnOwner", SparkMessageType.ERROR, "User is not the owner of selected facility"));
            }
        } else if (Boolean.FALSE.equals(facilityOwnersDbService.isPersonAnOwnerOfFacility(conn, perId, manager.getFo_wtf_id()))) {
            throw new SparkBusinessException(new S2Message("common.error.notAnOwner", SparkMessageType.ERROR, "User is not the owner of selected facility"));
        }

        if (!rcBroker.isPersonCodeKnown(manager.getPer_code(), manager.getPer_name(), manager.getPer_surname())) {
            throw new SparkBusinessException(new S2Message("common.error.personNotFoundInGr", SparkMessageType.ERROR, "Person was not found in GR"));
        }

        SprPersonsDAO person = personsDbService.loadRecordByParams(conn, " per_code = ? ", new SelectParamValue(manager.getPer_code()));
        if (person != null) {
            NtisFacilityOwnersDAO ownerRecord = facilityOwnersDbService.loadRecordByParams(conn,
                    " fo_per_id = ?::int  and fo_wtf_id = ?::int and fo_fo_id is null ", new SelectParamValue(person.getPer_id()),
                    new SelectParamValue(manager.getFo_wtf_id()));

            if (ownerRecord != null
                    && (ownerRecord.getFo_owner_type().equalsIgnoreCase(NtisFacilityOwnerType.MANAGER.getCode())
                            || ownerRecord.getFo_owner_type().equalsIgnoreCase(NtisFacilityOwnerType.OWNER.getCode()))
                    && (ownerRecord.getFo_date_to() == null || ownerRecord.getFo_date_to().after(new Date()))) {
                throw new SparkBusinessException(
                        new S2Message("common.error.managerAlreadyExists", SparkMessageType.ERROR, "The person already has manager rights for this facility"));
            } else {
                if (ownerRecord == null) {
                    ownerRecord = facilityOwnersDbService.newRecord();
                    ownerRecord.setFo_per_id(person.getPer_id());
                    ownerRecord.setFo_wtf_id(manager.getFo_wtf_id());
                }
                ownerRecord.setFo_date_from(Utils.getDate(manager.getFo_date_from()));
                ownerRecord.setFo_date_to(Utils.getDate(manager.getFo_date_to()));
                ownerRecord.setFo_owner_type(NtisFacilityOwnerType.MANAGER.getCode());
            }

            facilityOwnersDbService.saveRecord(conn, ownerRecord);
            updateManger(conn, manager.getFo_wtf_id(), ownerRecord.getFo_per_id(), perId, orgId, ownerRecord.getFo_id(), ownerRecord.getFo_date_from(),
                    ownerRecord.getFo_date_to());

        } else {
            SprPersonsDAO newPerson = personsDbService.newRecord();
            newPerson.setPer_code(manager.getPer_code());
            newPerson.setPer_code_exists(YesNo.Y.getCode());
            newPerson.setPer_lrt_resident(YesNo.Y.getCode());
            newPerson.setPer_name(manager.getPer_name());
            newPerson.setPer_surname(manager.getPer_surname());
            newPerson.setPer_date_of_birth(Utils.getDateFromString("1900-01-01", dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA)));
            personsDbService.saveRecord(conn, newPerson);
            NtisFacilityOwnersDAO newManager = facilityOwnersDbService.newRecord();
            newManager.setFo_per_id(newPerson.getPer_id());
            newManager.setFo_wtf_id(manager.getFo_wtf_id());
            newManager.setFo_date_from(Utils.getDate(manager.getFo_date_from()));
            newManager.setFo_date_to(Utils.getDate(manager.getFo_date_to()));
            newManager.setFo_owner_type(NtisFacilityOwnerType.MANAGER.getCode());
            newManager.setFo_managing_per_id(perId);
            newManager.setFo_managing_org_id(orgId);
            facilityOwnersDbService.saveRecord(conn, newManager);
            updateManger(conn, manager.getFo_wtf_id(), newPerson.getPer_id(), perId, orgId, newManager.getFo_id(), newManager.getFo_date_from(),
                    newManager.getFo_date_to());
        }
        return manager;
    }

    private void updateManger(Connection conn, Double wtfId, Double newPerId, Double perId, Double orgId, Double foId, Date dateFrom, Date dateTo)
            throws Exception {

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(
                """
                        insert into ntis_facility_owners(Fo_wtf_id, Fo_per_id, Fo_date_from, Fo_date_to, Fo_owner_type, fo_so_id, fo_managing_per_id, fo_managing_org_id, fo_fo_id)
                               (select oo.fo_wtf_id,
                                      ?,
                                      ?::timestamp,
                                      ?::timestamp,
                                      'MANAGER',
                                       oo.fo_so_id,
                                       ?,
                                       ?,
                                       ?
                                from ntis_facility_owners oo
                               where fo_wtf_id = ?::int and (fo_per_id = ?::int ));
                        """);

        stmt.addSelectParam(newPerId);
        stmt.addSelectParam(Utils.getDate(dateFrom));
        stmt.addSelectParam(Utils.getDate(dateTo));
        stmt.addSelectParam(perId.longValue());
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(foId);
        stmt.addSelectParam(wtfId);
        stmt.addSelectParam(perId);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);

    }
}
