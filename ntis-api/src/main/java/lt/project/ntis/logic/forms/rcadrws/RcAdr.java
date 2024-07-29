package lt.project.ntis.logic.forms.rcadrws;

import java.sql.Connection;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import lt.project.ntis.rcadrws.adm.ADMINISTRACINIS;
import lt.project.ntis.rcadrws.adrxy.ADRESAS;
import lt.project.ntis.rcadrws.gat.GATVE;
import lt.project.ntis.rcadrws.gyv.GYVENVIETE;
import lt.project.ntis.rcadrws.pat.PATALPA;

/**
 * Pagrindinė integracijos su Registrų Centro AdrWS sistema klasė.
 */
@Component
public class RcAdr {

    record Problem(String message, Exception exception) {

    }

    private static final Logger log = LoggerFactory.getLogger(RcAdr.class);

    static final String MDC_DATE_FROM = "ntis:dateFrom";

    static final String MDC_DATE_TO = "ntis:dateTo";

    static final String MDC_ADM_XML = "ntis:admXml";

    static final String MDC_GYV_XML = "ntis:gyvXml";

    static final String MDC_GAT_XML = "ntis:gatXml";

    static final String MDC_ADRXY_XML = "ntis:adrxyXml";

    static final String MDC_PAT_XML = "ntis:patXml";

    static final String MDC_SEN_CODE = "ntis:senCode";

    static final String MDC_SEN_ID = "ntis:senId";

    static final String MDC_RESIDENCE_CODE = "ntis:residenceCode";

    static final String MDC_RESIDENCE_ID = "ntis:reId";

    static final String MDC_STREET_CODE = "ntis:streetCode";

    static final String MDC_STREET_ID = "ntis:strId";

    static final String MDC_AOB_CODE = "ntis:aobCode";

    static final String MDC_AOB_ID = "ntis:adsId";

    static final String MDC_PAT_CODE = "ntis:patCode";

    static final String MDC_PAT_ID = "ntis:aplId";

    @Autowired
    RcAdrWs rcAdrWs;

    @Autowired
    RcAdrJaxb rcAdrJaxb;

    @Autowired
    RcAdrDb rcAdrDb;

    void collectAndApplyUpdates(Connection conn, SprJobDefinitionsDAO dao, Map<LocalDate, LocalDate> jobIntervals, Consumer<Problem> problemsConsumer)
            throws Exception {
        if (jobIntervals.isEmpty()) {
            log.info("Job has no intervals to cover for execution parameter {}.", dao.getJde_execution_parameter());
        } else {
            for (Map.Entry<LocalDate, LocalDate> entry : jobIntervals.entrySet()) {
                dao = doUpdatesForDaterange(conn, entry.getKey(), entry.getValue(), dao, problemsConsumer);
            }
        }
    }

    private SprJobDefinitionsDAO doUpdatesForDaterange(Connection conn, LocalDate dateFromInclusive, LocalDate dateToInclusive, SprJobDefinitionsDAO dao,
            Consumer<Problem> problemsConsumer) throws Exception {
        doUpdatesForDaterange(conn, dateFromInclusive, dateToInclusive, problemsConsumer);
        LocalDate newDateFromInclusive = dateToInclusive.plusDays(1);
        dao.setJde_execution_parameter(newDateFromInclusive.toString());
        SprJobDefinitionsDAO result = rcAdrDb.updateRecord(conn, dao);
        conn.commit();
        return result;
    }

    private void doUpdatesForDaterange(Connection conn, LocalDate dateFrom, LocalDate dateTo, Consumer<Problem> problemsConsumer) throws Exception {
        MDC.put(MDC_DATE_FROM, dateFrom.toString());
        MDC.put(MDC_DATE_TO, dateTo.toString());

        List<ADMINISTRACINIS> administracinisList = getNewAdministracinisData(dateFrom, dateTo);
        List<GYVENVIETE> gyvenvieteList = getNewGyvenvieteData(dateFrom, dateTo);
        List<GATVE> gatveList = getNewGatveData(dateFrom, dateTo);
        List<ADRESAS> adresasList = getNewAdresasData(dateFrom, dateTo);
        List<PATALPA> patalpaList = getNewPatalpaData(dateFrom, dateTo);

        applyAdministracinisData(conn, administracinisList, problemsConsumer);
        applyGyvenvieteData(conn, gyvenvieteList, problemsConsumer);
        applyGatveData(conn, gatveList, problemsConsumer);
        applyAdresasData(conn, adresasList, problemsConsumer);
        applyPatalpaData(conn, patalpaList, problemsConsumer);
    }

    private List<ADMINISTRACINIS> getNewAdministracinisData(LocalDate dateFrom, LocalDate dateTo) throws Exception {
        log.info("Retrieving ADM (seniunijos) data for interval {} - {}.", dateFrom, dateTo);
        long t1 = System.currentTimeMillis();
        String admXml = rcAdrWs.getAdrData(DataType.ADM, dateFrom, dateTo);
        MDC.put(MDC_ADM_XML, admXml);
        List<ADMINISTRACINIS> result = rcAdrJaxb.collectResponseAdm(admXml).getADMINISTRACINIS();
        log.info("Retrieved {} AMD (seniunijos) data in {} ms.", result.size(), (System.currentTimeMillis() - t1));
        return result;
    }

    private List<GYVENVIETE> getNewGyvenvieteData(LocalDate dateFrom, LocalDate dateTo) throws Exception {
        log.info("Retrieving GYV (gyvenvietes) data for interval {} - {}.", dateFrom, dateTo);
        long t1 = System.currentTimeMillis();
        String gyvXml = rcAdrWs.getAdrData(DataType.GYV, dateFrom, dateTo);
        MDC.put(MDC_GYV_XML, gyvXml);
        List<GYVENVIETE> result = rcAdrJaxb.collectResponseGyv(gyvXml).getGYVENVIETE();
        log.info("Retrieved {} GYV (gyvenvietes) data in {} ms.", result.size(), (System.currentTimeMillis() - t1));
        return result;
    }

    private List<GATVE> getNewGatveData(LocalDate dateFrom, LocalDate dateTo) throws Exception {
        log.info("Retrieving GAT (gatves) data for interval {} - {}.", dateFrom, dateTo);
        long t1 = System.currentTimeMillis();
        String gatXml = rcAdrWs.getAdrData(DataType.GAT, dateFrom, dateTo);
        MDC.put(MDC_GAT_XML, gatXml);
        List<GATVE> result = rcAdrJaxb.collectResponseGat(gatXml).getGATVE();
        log.info("Retrieved {} GAT (gatves) data in {} ms.", result.size(), (System.currentTimeMillis() - t1));
        return result;
    }

    private List<ADRESAS> getNewAdresasData(LocalDate dateFrom, LocalDate dateTo) throws Exception {
        log.info("Retrieving ADRXY (adresai) data for interval {} - {}.", dateFrom, dateTo);
        long t1 = System.currentTimeMillis();
        String adrxyXml = rcAdrWs.getAdrData(DataType.ADRXY, dateFrom, dateTo);
        MDC.put(MDC_ADRXY_XML, adrxyXml);
        List<ADRESAS> result = rcAdrJaxb.collectResponseAdrxy(adrxyXml).getADRESAS();
        log.info("Retrieved {} ADRXY (adresai) data in {} ms.", result.size(), (System.currentTimeMillis() - t1));
        return result;
    }

    private List<PATALPA> getNewPatalpaData(LocalDate dateFrom, LocalDate dateTo) throws Exception {
        log.info("Retrieving PAT (patalpos) data for interval {} - {}.", dateFrom, dateTo);
        long t1 = System.currentTimeMillis();
        String patXml = rcAdrWs.getAdrData(DataType.PAT, dateFrom, dateTo);
        MDC.put(MDC_PAT_XML, patXml);
        List<PATALPA> result = rcAdrJaxb.collectResponsePat(patXml).getPATALPA();
        log.info("Retrieved {} PAT (patalpos) data in {} ms.", result.size(), (System.currentTimeMillis() - t1));
        return result;
    }

    private void applyAdministracinisData(Connection conn, List<ADMINISTRACINIS> data, Consumer<Problem> problemsConsumer) throws Exception {
        if (data.isEmpty()) {
            log.info("No new records of type {}.", ADMINISTRACINIS.class.getSimpleName());
        } else {
            for (ADMINISTRACINIS administracinis : data) {
                MDC.remove(MDC_SEN_CODE);
                MDC.remove(MDC_SEN_ID);
                rcAdrDb.changeSeniunijaData(conn, administracinis).ifPresent(problemsConsumer);
            }
        }
    }

    private void applyGyvenvieteData(Connection conn, List<GYVENVIETE> data, Consumer<Problem> problemsConsumer) throws Exception {
        if (data.isEmpty()) {
            log.info("No new records of type {}.", GYVENVIETE.class.getSimpleName());
        } else {
            for (GYVENVIETE gyvenviete : data) {
                MDC.remove(MDC_RESIDENCE_CODE);
                MDC.remove(MDC_RESIDENCE_ID);
                rcAdrDb.changeResidenceData(conn, gyvenviete).ifPresent(problemsConsumer);
            }
        }
    }

    private void applyGatveData(Connection conn, List<GATVE> data, Consumer<Problem> problemsConsumer) throws Exception {
        if (data.isEmpty()) {
            log.info("No new records of type {}.", GATVE.class.getSimpleName());
        } else {
            for (GATVE gatve : data) {
                MDC.remove(MDC_STREET_CODE);
                MDC.remove(MDC_STREET_ID);
                rcAdrDb.changeStreetData(conn, gatve).ifPresent(problemsConsumer);
            }
        }
    }

    private void applyAdresasData(Connection conn, List<ADRESAS> data, Consumer<Problem> problemsConsumer) throws Exception {
        if (data.isEmpty()) {
            log.info("No new records of type {}.", ADRESAS.class.getSimpleName());
        } else {
            for (ADRESAS adresas : data) {
                MDC.remove(MDC_AOB_CODE);
                MDC.remove(MDC_AOB_ID);
                rcAdrDb.changeAddressData(conn, adresas).ifPresent(problemsConsumer);
            }
        }
    }

    private void applyPatalpaData(Connection conn, List<PATALPA> data, Consumer<Problem> problemsConsumer) throws Exception {
        if (data.isEmpty()) {
            log.info("No new records of type {}.", PATALPA.class.getSimpleName());
        } else {
            for (PATALPA patalpa : data) {
                MDC.remove(MDC_PAT_CODE);
                MDC.remove(MDC_PAT_ID);
                rcAdrDb.changePatalpaData(conn, patalpa).ifPresent(problemsConsumer);
            }
        }
    }

    static boolean isBlank(String data) {
        return data == null || data.isBlank();
    }

    static String safeToString(Double data) {
        return data == null ? "null" : data.toString();
    }

    static Double toDouble(String data) {
        return Utils.getDouble(data);
    }

    static Date toDate(String data) throws ParseException {
        return Utils.getDateFromString(data, DBStatementManager.DATE_FORMAT_DAY_DEFAULT_JAVA);
    }

    static LocalDate toLocalDate(String data) {
        return LocalDate.parse(data);
    }

}
