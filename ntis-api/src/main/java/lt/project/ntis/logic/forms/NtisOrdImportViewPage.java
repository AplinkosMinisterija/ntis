package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.List;

import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.dao.query.security.QueryResultSecurityManager;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.dao.NtisOrderFileDataDAO;
import lt.project.ntis.dao.NtisOrderFileDataErrsDAO;
import lt.project.ntis.dao.NtisOrderFilesDAO;
import lt.project.ntis.dao.NtisServicesDAO;
import lt.project.ntis.enums.NtisServiceItemType;
import lt.project.ntis.models.NtisOrdImportFile;
import lt.project.ntis.service.NtisOrderFileDataDBService;
import lt.project.ntis.service.NtisOrderFileDataErrsDBService;
import lt.project.ntis.service.NtisOrderFilesDBService;
import lt.project.ntis.service.NtisServicesDBService;

/**
 * Klasė skirta Importuotų užsakymų failo peržiūros formos biznio logikai
 * apibrėžti
 * 
 */

@Component
public class NtisOrdImportViewPage extends FormBase {

    public static final String PASL_ADMIN_ACTIONS = "PASL_ADMIN_ACTIONS";

    public static final String PASL_ADMIN_ACTIONS_DESC = "Paslaugų teikimo įmonės administratoriaus veiksmai";

    @Override
    public String getFormName() {
        return "NTIS_ORD_IMPORT_VIEW_PAGE";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Importuotas užsakymų failas", "Importuoto užsakymų failo peržiūros forma");
        addFormActions(FormBase.ACTION_CREATE, FormBase.ACTION_UPDATE, FormBase.ACTION_DELETE);
        addFormAction(PASL_ADMIN_ACTIONS, PASL_ADMIN_ACTIONS_DESC, PASL_ADMIN_ACTIONS);
    }

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbStatementManager;

    private final FileStorageService fileStorageService;

    private final NtisOrderFilesDBService ntisOrderFilesDBService;

    private final NtisOrderFileDataDBService ntisOrderFileDataDBService;

    private final NtisOrderFileDataErrsDBService ntisOrderFileDataErrsDBService;

    private final NtisServicesDBService ntisServicesDBService;

    private final SprFilesDBService sprFilesDBService;

    public NtisOrdImportViewPage(BaseControllerJDBC baseControllerJDBC, DBStatementManager dbStatementManager, FileStorageService fileStorageService,
            NtisOrderFilesDBService ntisOrderFilesDBService, NtisOrderFileDataDBService ntisOrderFileDataDBService,
            NtisOrderFileDataErrsDBService ntisOrderFileDataErrsDBService, NtisServicesDBService ntisServicesDBService, SprFilesDBService sprFilesDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbStatementManager = dbStatementManager;
        this.fileStorageService = fileStorageService;
        this.ntisOrderFilesDBService = ntisOrderFilesDBService;
        this.ntisOrderFileDataDBService = ntisOrderFileDataDBService;
        this.ntisOrderFileDataErrsDBService = ntisOrderFileDataErrsDBService;
        this.ntisServicesDBService = ntisServicesDBService;
        this.sprFilesDBService = sprFilesDBService;
    }

    /**
     * Metodas grąžins importuotą paslaugų užsakymų failą importo failą, jame
     * nustatytų klaidų ir eilučių skaičių peržiūrai
     * 
     * @param conn             - prisijungimas prie DB
     * @param recordIdentifier - įrašo identifikatorius
     * @param orfId            - importuoto failo įrašo id
     * @param orgId            - failui priskirtos organizacijos id
     * @param orgIdSes         - sesijos organizacijos id
     * @return NtisOrdImportFile objektas
     * @throws Exception
     */
    public NtisOrdImportFile getFileRecord(Connection conn, String orfId, String orgId, Double orgIdSes) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOrdImportViewPage.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams("""
                with file_errors as
                                   (select coalesce(count(*), 0) as total_errors, orfde_orf_id
                                      from ntis_order_file_data_errs
                                     where orfde_orf_id = ?::int
                                     group by orfde_orf_id),
                     file_data as
                                   (select coalesce(count(*), 0) as total_records, orfd_orf_id
                                      from ntis_order_file_data
                                     where orfd_orf_id = ?::int
                                     group by orfd_orf_id)
                 select orf.orf_id,
                 srv.srv_type,
                 to_char(orf.orf_import_date, '%s') as orf_import_date,
                 to_char(orf.orf_status_date, '%s') as orf_status_date,
                 concat_ws(', ', org.org_type ,org.org_name , org.org_address)  as org_name,
                 orf.orf_status,
                 fer.total_errors,
                 fed.total_records,
                 fil.fil_content_type,
                 fil.fil_key,
                 fil.fil_name,
                 fil.fil_size,
                 fil.fil_status,
                 to_char(fil.fil_status_date, '%s') as fil_status_date
                 from ntis.ntis_order_files orf
                 left join ntis.ntis_services srv on srv.srv_id = orf.orf_srv_id
                 inner join spark.spr_organizations org on org.org_id = orf.orf_org_id
                 left join file_errors fer on fer.orfde_orf_id = orf.orf_id
                 left join file_data fed on fed.orfd_orf_id = orf.orf_id
                 inner join spr_files fil on fil.fil_id = orf.orf_fil_id and orf.orf_id = ?::int and orf.orf_org_id = ?::int
                """.formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        stmt.addSelectParam(orfId);
        stmt.addSelectParam(orfId);
        stmt.addSelectParam(orfId);

        if (this.isFormActionAssigned(conn, NtisOrdImportViewPage.PASL_ADMIN_ACTIONS)) {
            stmt.addSelectParam(orgIdSes);
        } else if (!this.isFormActionAssigned(conn, NtisOrdImportViewPage.PASL_ADMIN_ACTIONS)) {
            stmt.addSelectParam(Utils.getDouble(orgId));
        }
        List<NtisOrdImportFile> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisOrdImportFile.class);
        if (data != null && !data.isEmpty()) {
            data.get(0).setFil_key(fileStorageService.encryptFileKey(data.get(0).getFil_key()));
            return data.get(0);
        } else {
            throw new Exception("No file with id " + orfId + " found");
        }
    }

    /**
     * Metodas grąžins užsakymų importo failo įraše nuskaitytų eilučių sąrašą
     * 
     * @param conn   - prisijungimas prie DB
     * @param params - select parametrai
     * @param orgId  - sesijos organizacijos id
     * @return JSON objektas
     * @throws Exception TODO: Pridėti atitinkamus duomenis, kai bus aišku, kas turi
     *                   būti rodoma priklausomai nuo to, kokios paslaugos
     *                   užsakymai.
     */
    public String getFileLinesList(Connection conn, SelectRequestParams params, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOrdImportViewPage.ACTION_READ);
        Double orfId = Utils.getDouble(params.getParamList().get("orfId"));
        NtisOrderFilesDAO orderFile = this.ntisOrderFilesDBService.loadRecord(conn, orfId);
        NtisServicesDAO serviceDAO = null;
        if (orderFile != null && orderFile.getOrf_id() != null) {
            serviceDAO = this.ntisServicesDBService.loadRecord(conn, orderFile.getOrf_srv_id());

        }
        String srvType = null;
        if (serviceDAO != null) {
            srvType = serviceDAO.getSrv_type();
        }
        StatementAndParams stmt = new StatementAndParams();
        if (srvType.equalsIgnoreCase(NtisServiceItemType.PRIEZIURA.getCode())) {
            stmt.setStatement("""
                    select
                        (orfd.orfd_eil_nr + 1) as orfd_eil_nr,
                        coalesce(ad.full_address_text, wtf.wtf_facility_latitude || ', ' || wtf.wtf_facility_longitude) AS wtf_address,
                        orfd.orfd_uzsakymo_data,
                        orfd.orfd_uzsakovo_tel,
                        orfd.orfd_uzsakovo_email,
                        orfd.orfd_atlikimo_data,
                        orfd.orfd_atlikti_darbai,
                        orfd.orfd_uzsakovo_komentaras
                      from ntis_order_file_data orfd
                      left join ntis_wastewater_treatment_faci wtf on wtf.wtf_id = orfd.orfd_wtf_id
                      left join ntis_address_vw AS ad ON ad.address_id = wtf.wtf_ad_id
                      inner join ntis_order_files orf on orf.orf_id = orfd.orfd_orf_id and orf.orf_id = ?::int and orf.orf_org_id = ?::int
                    """);

        } else if (srvType.equalsIgnoreCase(NtisServiceItemType.VEZIMAS.getCode())) {
            stmt.setStatement("""
                    select
                        (orfd.orfd_eil_nr + 1) as orfd_eil_nr,
                        coalesce(ad.full_address_text, wtf.wtf_facility_latitude || ', ' || wtf.wtf_facility_longitude) AS wtf_address,
                        orfd.orfd_uzsakymo_data,
                        orfd.orfd_uzsakovo_tel,
                        orfd.orfd_uzsakovo_email,
                        orfd.orfd_isvezimo_data,
                        orfd.orfd_isveztas_kiekis,
                        orfd.orfd_transporto_priemone,
                        orfd.orfd_uzsakymo_informacija,
                        orfd.orfd_uzsakovo_komentaras
                      from ntis_order_file_data orfd
                      left join ntis_wastewater_treatment_faci wtf on wtf.wtf_id = orfd.orfd_wtf_id
                      left join ntis_address_vw AS ad ON ad.address_id = wtf.wtf_ad_id
                      inner join ntis_order_files orf on orf.orf_id = orfd.orfd_orf_id and orf.orf_id = ?::int and orf.orf_org_id = ?::int
                    """);

        } else if (srvType.equalsIgnoreCase(NtisServiceItemType.TYRIMAI.getCode())) {
            stmt.setStatement("""
                         select
                             (orfd.orfd_eil_nr + 1) as orfd_eil_nr,
                             coalesce(ad.full_address_text, wtf.wtf_facility_latitude || ', ' || wtf.wtf_facility_longitude) AS wtf_address,
                             orfd.orfd_uzsakymo_data,
                             orfd.orfd_uzsakovo_tel,
                             orfd.orfd_uzsakovo_email,
                             orfd.orfd_uzsakovas,
                             orfd.orfd_laboratorijos_komentaras,
                             orfd.orfd_deguonis,
                             orfd.orfd_skendincios,
                             orfd.orfd_azotas,
                             orfd.orfd_fosforas,
                             orfd.orfd_meginio_data,
                             orfd.orfd_meginio_darbuotojas,
                             orfd.orfd_tyrimo_data,
                    orfd.orfd_tyrimo_darbuotojas,
                             orfd.orfd_pastaba_rezultatams
                           from ntis_order_file_data orfd
                           left join ntis_wastewater_treatment_faci wtf on wtf.wtf_id = orfd.orfd_wtf_id
                           left join ntis_address_vw AS ad ON ad.address_id = wtf.wtf_ad_id
                           inner join ntis_order_files orf on orf.orf_id = orfd.orfd_orf_id and orf.orf_id = ?::int and orf.orf_org_id = ?::int
                         """);
        }
        stmt.addSelectParam(Utils.getDouble(params.getParamList().get("orfId")));
        if (this.isFormActionAssigned(conn, NtisOrdImportViewPage.PASL_ADMIN_ACTIONS)) {
            stmt.addSelectParam(orgId);
        } else {
            stmt.addSelectParam(Utils.getDouble(params.getParamList().get("orgId")));
        }

        if (params.getPagingParams().getOrder_clause() == null) {
            stmt.setStatementOrderPart("""
                       order by orfd.orfd_eil_nr asc
                    """);
        }

        QueryResultSecurityManager<SprBackendUserSession> sqm = new QueryResultSecurityManager<SprBackendUserSession>();
        FormActions formActions = this.getFormActions(conn);
        sqm.setFormActions(formActions);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Metodas grąžins importuoto užsakymų failo importo įraše nuskaitytų klaidų
     * sąrašą
     * 
     * @param conn   - prisijungimas prie DB
     * @param params - select parametrai
     * @param orgId  - sesijos organizacijos id
     * @return JSON objektas
     * @throws Exception
     */
    public String getErrorsList(Connection conn, SelectRequestParams params, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOrdImportViewPage.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams("""
                select
                     orfde.orfde_id,
                     orfde.orfde_column_nr,
                     orfde.orfde_column_value,
                     orfde.orfde_msg_code,
                     orfde.orfde_msg_text,
                     (orfd.orfd_eil_nr + 1) as orfd_eil_nr
                from ntis_order_file_data_errs orfde
                inner join ntis_order_files orf on orf.orf_id = orfde.orfde_orf_id and orf.orf_id = ?::int and orf.orf_org_id = ?::int
                left join ntis_order_file_data orfd on orfd.orfd_orf_id = orf.orf_id and orfd.orfd_id = orfde.orfde_orfd_id
                """);
        stmt.setWhereExists(true);
        stmt.setStatementOrderPart("order by orfd_eil_nr");
        stmt.addSelectParam(Utils.getDouble(params.getParamList().get("orfId")));
        if (this.isFormActionAssigned(conn, NtisOrdImportViewPage.PASL_ADMIN_ACTIONS)) {
            stmt.addSelectParam(orgId);
        } else {
            stmt.addSelectParam(Utils.getDouble(params.getParamList().get("orgId")));
        }

        QueryResultSecurityManager<SprBackendUserSession> sqm = new QueryResultSecurityManager<SprBackendUserSession>();
        FormActions formActions = this.getFormActions(conn);
        sqm.setFormActions(formActions);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Pagal perduodamo failo duomenis, metodas ištrins failo įrašą iš
     * NTIS_ORDER_FILES lentelės ir su juo susijusius įrašus kitose lentelėse.
     * 
     * @param file - SprFile objektas
     * @throws Exception
     */
    public void delete(Connection conn, SprFile file, Double orgId) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, NtisOrdImportViewPage.ACTION_DELETE);
        String decryptedKey = fileStorageService.decryptFileKey(file.getFil_key());
        SprFilesDAO sprFile = sprFilesDBService.loadRecordByKey(conn, decryptedKey);
        NtisOrderFilesDAO orderFile = ntisOrderFilesDBService.loadRecordByParams(conn, "orf_fil_id = ?::int", new SelectParamValue(sprFile.getFil_id()));
        if (this.isFormActionAssigned(conn, NtisOrdImportViewPage.PASL_ADMIN_ACTIONS)) {
            if (orderFile.getOrf_org_id().compareTo(orgId) != 0) {
                throw new SparkBusinessException(
                        new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
            }
        }
        ntisOrderFileDataErrsDBService.deleteRecordByOrfId(conn, orderFile.getOrf_id());
        ntisOrderFileDataDBService.deleteRecordByOrfId(conn, orderFile.getOrf_id());
        ntisOrderFilesDBService.deleteRecord(conn, orderFile.getOrf_id());
        fileStorageService.deleteFile(decryptedKey, sprFile.getFil_path());
        sprFilesDBService.deleteRecord(conn, sprFile.getFil_id());
    }

    /**
     * Metodas grąžins rastų nuotekų tvarkymo įrenginių sąrašą užsakymo importo
     * failo eilutėje pagal pateikiamą įraše pažymėtos klaidos id
     * 
     * @param conn    - prisijungimas prie DB
     * @param orfdeId - klaidos įrašo id
     * @return JSON objektas
     * @throws Exception
     */
    public String getAvailableWtfs(Connection conn, Double orfdeId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOrdImportViewPage.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams("""
                select
                     wtf.wtf_id,
                     adr.ad_address as wtf_address,
                     coalesce(rfc.rfc_meaning, wtf.wtf_type) as wtf_type,
                     orfde.orfde_id
                    from ntis_order_file_data_errs orfde,
                         ntis_order_file_data orfd,
                         ntis_adr_addresses adr,
                         ntis_adr_stats ads,
                         ntis_wastewater_treatment_faci wtf
                         left join spr_ref_codes_vw rfc on rfc_code = wtf.wtf_type and rfc_domain = 'NTIS_WTF_TYPE' and rft_lang = ?
                where orfde.orfde_id = ?::int
                  and orfde.orfde_orfd_id = orfd.orfd_id
                  and orfd_pastato_adr_kodas::numeric = ads.ads_aob_code
                  and wtf.wtf_ad_id = adr.ad_id
                  and adr.ad_ads_id = ads.ads_id

                """);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(orfdeId);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt);
    }

    public void updateWithSelectedWtf(Connection conn, Double wtfId, Double orfdeId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOrdImportViewPage.ACTION_UPDATE);
        NtisOrderFileDataErrsDAO fileDataErrsDAO = this.ntisOrderFileDataErrsDBService.loadRecord(conn, orfdeId);
        NtisOrderFileDataDAO orderFileDataDAO = this.ntisOrderFileDataDBService.loadRecord(conn, fileDataErrsDAO.getOrfde_orfd_id());
        orderFileDataDAO.setOrfd_wtf_id(wtfId);
        orderFileDataDAO.setC02(null);
        this.ntisOrderFileDataDBService.saveRecord(conn, orderFileDataDAO);
        this.ntisOrderFileDataErrsDBService.deleteRecord(conn, fileDataErrsDAO);
    }
}
