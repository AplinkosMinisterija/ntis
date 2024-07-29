package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisCwFilesDAO;
import lt.project.ntis.models.NtisWastewaterDataImportFile;
import lt.project.ntis.service.NtisCwFileDataDBService;
import lt.project.ntis.service.NtisCwFileDataErrsDBService;
import lt.project.ntis.service.NtisCwFilesDBService;

/**
 * Klasė skirta formos "Centralizuoto nuotekų tvarkymo duomenų peržiūra" (formos kodas N4220) biznio logikai apibrėžti
 */
@Component
public class NtisCentralizedWastewaterDataViewPage extends FormBase {

    public static final String VAND_ADMIN_ACTIONS = "VAND_ADMIN_ACTIONS";

    public static final String VAND_ADMIN_ACTIONS_DESC = "Vandentvarkos administratoriaus veiksmai";

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    NtisCwFilesDBService cwFilesDBService;

    @Autowired
    SprFilesDBService sprFilesDBService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    NtisCwFileDataDBService cwFileDataDBService;

    @Autowired
    NtisCwFileDataErrsDBService cwFileDataErrsDBService;

    @Override
    public String getFormName() {
        return "NTIS_CENTRALIZED_WASTEWATER_DATA_VIEW_PAGE";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Centralizuoto nuotekų tvarkymo duomenų peržiūra", "Centralizuoto nuotekų tvarkymo duomenų peržiūros forma");
        addFormActions(FormBase.ACTION_CREATE, FormBase.ACTION_UPDATE, FormBase.ACTION_DELETE);
        addFormAction(VAND_ADMIN_ACTIONS, VAND_ADMIN_ACTIONS_DESC, VAND_ADMIN_ACTIONS);
    }

    /**
     * Metodas grąžins centralizuoto nuotekų tvarkymo bylos importo failą, jame nustatytų klaidų ir eilučių skaičių peržiūrai
     * @param conn - prisijungimas prie DB
     * @param recordIdentifier - įrašo identifikatorius
     * @param cwfId - failo įrašo id
     * @param orgId - failui priskirtos organizacijos id
     * @param orgIdSes - sesijos organizacijos id
     * @return NtisWastewaterDataImportFile objektas
     * @throws Exception
     */
    public NtisWastewaterDataImportFile getFileRecord(Connection conn, String cwfId, String orgId, Double orgIdSes) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCentralizedWastewaterDataViewPage.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("with file_errors as " //
                + "                 (select coalesce(count(*), 0) as total_errors, cwfde_cwf_id " //
                + "                 from ntis_cw_file_data_errs " //
                + "                 where cwfde_cwf_id = ?::int " //
                + "                 group by cwfde_cwf_id), "//
                + "             file_data as " //
                + "                 (select coalesce(count(*), 0) as total_records, cwfd_cwf_id " //
                + "                 from ntis_cw_file_data " //
                + "                 where cwfd_cwf_id = ?::int " //
                + "                 group by cwfd_cwf_id) " //
                + "select cw.cwf_id, " //
                + "to_char(cw.cwf_import_date, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as cwf_import_date, " //
                + "to_char(cw.cwf_status_date, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as cwf_status_date, " //
                + "concat_ws(', ', org.org_type ,org.org_name , org.org_address)  as org_name, " //
                + "cw.cwf_status, " //
                + "fe.total_errors, " //
                + "fd.total_records, " //
                + "fil.fil_content_type, " //
                + "fil.fil_key, " //
                + "fil.fil_name, " //
                + "fil.fil_size, " //
                + "fil.fil_status, " //
                + "to_char(fil.fil_status_date, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as fil_status_date " //
                + "from ntis_cw_files cw " //
                + "inner join spark.spr_organizations org on org.org_id = cw.cwf_org_id "//
                + "left join file_errors fe on fe.cwfde_cwf_id = cw.cwf_id "//
                + "left join file_data fd on fd.cwfd_cwf_id = cw.cwf_id "//
                + "inner join spr_files fil on fil.fil_id = cw.cwf_fil_id and cw.cwf_id = ?::int and cw.cwf_org_id = ?::int ");
        stmt.addSelectParam(Utils.getDouble(cwfId));
        stmt.addSelectParam(Utils.getDouble(cwfId));
        stmt.addSelectParam(Utils.getDouble(cwfId));
        if (hasUserRole(conn, NtisRolesConstants.NTIS_ADMIN) && orgId != null) {
            stmt.addSelectParam(Utils.getDouble(orgId));
        } else if (this.isFormActionAssigned(conn, NtisCentralizedWastewaterDataViewPage.VAND_ADMIN_ACTIONS)) {
            stmt.addSelectParam(orgIdSes);
        } else if (!this.isFormActionAssigned(conn, NtisCentralizedWastewaterDataViewPage.VAND_ADMIN_ACTIONS)) {
            stmt.addSelectParam(Utils.getDouble(orgId));
        }
        List<NtisWastewaterDataImportFile> data = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisWastewaterDataImportFile.class);
        if (data != null && !data.isEmpty()) {
            data.get(0).setFil_key(fileStorageService.encryptFileKey(data.get(0).getFil_key()));
            return data.get(0);
        } else {
            throw new Exception("No file with id " + cwfId + " found");
        }
    }

    /**
     * Pagal nurodytą įrašo identifikatorių, metodas atnaujins importuotos bylos statusą į galutinį
     * @param recordIdentifier - įrašo identifikatorius
     * @orgId - sesijos organizacijos id
     * @throws Exception
     */
    public void updateExistingFile(Connection conn, String id, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCentralizedWastewaterDataViewPage.ACTION_UPDATE);
        if (this.isFormActionAssigned(conn, NtisCentralizedWastewaterDataViewPage.VAND_ADMIN_ACTIONS)) {
            NtisCwFilesDAO fileDAO = cwFilesDBService.loadRecordByParams(conn, "cwf_id = ?::int and cwf_org_id=?::int",
                    new SelectParamValue(Utils.getDouble(id)), new SelectParamValue(orgId));
            if (fileDAO == null || fileDAO.getCwf_id() == null) {
                throw new SparkBusinessException(
                        new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
            }
        }
        StatementAndParams stmt = new StatementAndParams("CALL ntis.update_building_agreements(?::integer, ?::integer)");
        stmt.setWhereExists(true);
        stmt.addSelectParam(id);
        stmt.addSelectParam(usrId);
        queryController.adjustRecordsInDB(conn, stmt);
    }

    /**
     * Pagal perduodamo failo duomenis, metodas ištrins failo įrašą iš NTIS_CW_FILES lentelės ir su juo susijusius įrašus kitose lentelėse.
     * @param file - SprFile objektas
     * @throws Exception
     */
    public void delete(Connection conn, SprFile file, Double orgId) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, NtisCentralizedWastewaterDataViewPage.ACTION_DELETE);
        String decryptedKey = fileStorageService.decryptFileKey(file.getFil_key());
        SprFilesDAO sprFile = sprFilesDBService.loadRecordByKey(conn, decryptedKey);
        NtisCwFilesDAO cwFile = cwFilesDBService.loadRecordByFileId(conn, sprFile.getFil_id());
        if (this.isFormActionAssigned(conn, NtisCentralizedWastewaterDataViewPage.VAND_ADMIN_ACTIONS)) {
            if (cwFile.getCwf_org_id().compareTo(orgId) != 0) {
                throw new SparkBusinessException(
                        new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
            }
        }
        cwFileDataErrsDBService.deleteRecordByCwfId(conn, cwFile.getCwf_id());
        cwFileDataDBService.deleteRecordByCwfId(conn, cwFile.getCwf_id());
        cwFilesDBService.deleteRecord(conn, cwFile.getCwf_id());
        fileStorageService.deleteFile(decryptedKey, sprFile.getFil_path());
        sprFilesDBService.deleteRecord(conn, sprFile.getFil_id());
    }
}