package lt.project.ntis.job.request.centralized;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import lt.project.ntis.app.job.executor.impl.data.loader.csv.ExecuteCentralDataValidationTask;
import lt.project.ntis.dao.NtisCwFilesDAO;
import lt.project.ntis.dao.NtisFacilityOwnersDAO;

@Service("NTIS_UPDATE_CW_ERROR_FILES")
public class NtisUpdateCwErrorFiles extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(NtisUpdateCwErrorFiles.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception {
        log.debug("Collecting cwf_id and cwf_usr_id of centralized files with cw_fil_err_processing status that are not currently being processed");
        StatementAndParams statementAndParams = new StatementAndParams("""
                select cwf_id,
                       cwf_usr_id
                  from ntis.ntis_cw_files
                 where cwf_status = 'CW_FIL_ERR_PROCESSING'
                   and cwf_id in (select cwf_id
                                    from ntis_cw_files where cwf_status = 'CW_FIL_ERR_PROCESSING' for update skip locked)
                                    """);

        List<NtisCwFilesDAO> result = baseControllerJDBC.selectQueryAsObjectArrayList(conn, statementAndParams, NtisCwFilesDAO.class);

        for (NtisCwFilesDAO file : result) {

            try {
                StatementAndParams stmtDel = new StatementAndParams(" delete from ntis_cw_file_data_errs where cwfde_cwf_id = ?::int");
                stmtDel.addSelectParam(file.getCwf_id());
                this.baseControllerJDBC.adjustRecordsInDB(conn, stmtDel);

                StatementAndParams stmt = new StatementAndParams("CALL ntis.validate_data_import(?::integer, ?::integer)");
                stmt.addSelectParam(file.getCwf_id());
                stmt.addSelectParam(file.getCwf_usr_id());
                PreparedStatement ps = conn.prepareStatement(stmt.getStatemenWithParams());
                stmt.setValues(ps);
                ps.execute();
            } catch (Exception ex) {
                log.debug(ex.getMessage());

                conn.rollback();
                StatementAndParams stmt2 = new StatementAndParams(
                        "update ntis.ntis_cw_files set cwf_status = 'CW_FIL_PENDING_ERR', cwf_usr_id = ?::int where cwf_id = ?::int");
                stmt2.addSelectParam(file.getCwf_usr_id());
                stmt2.addSelectParam(file.getCwf_id());
                baseControllerJDBC.adjustRecordsInDB(conn, stmt2);
                conn.commit();
            }
        }
    }
}
