package lt.project.ntis.job.request.map;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.app.job.request.impl.CmdJobRequest;
import eu.itreegroup.spark.app.scheduler.ExecutorJob;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;

/**
 * Update MAP OBJECTS
 */

@Service("NTIS_MAP_OBJECTS_UPDATE")
public class UpdateMapObjects extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(UpdateMapObjects.class);

    @Autowired
    CmdJobRequest cmdJobRequest;

    @Autowired
    ExecutorJob executorJob;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception {
        log.info("START UPDATE_MAP_OBJECTS.");

        StatementAndParams statementAndParams = new StatementAndParams("""
                select case when
                    (SELECT max(rec_timestamp)
                    from ntis.ntis_served_objects) >
                     (select max(jrq_end_date)
                      from spark.spr_job_definitions
                       join spark.SPR_JOB_REQUESTS on jrq_jde_id = jde_id
                      where jde_code = 'NTIS_MAP_OBJECTS_UPDATE'
                       and jrq_status = 'COMPLETED') then 'Y' else 'N' end result
                                    """);
        List<HashMap<String, String>> res = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, statementAndParams);

        if ("Y".equals(getResult(res))) {
            log.debug("   BEGIN  SELECT tiles.update_discharges_tiles() as result");
            res = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, new StatementAndParams("SELECT tiles.update_discharges_tiles() as result"));
            log.debug(getResult(res));
            log.debug("   END  SELECT tiles.update_discharges_tiles() as result");
            Double jrqDischarges = this.cmdJobRequest.createJobRequest(conn, "NTIS_DISCHARGES_CLEAR_CACHE", null, Languages.LT, null);
            conn.commit();
            this.executorJob.execute(jrqDischarges);

            log.debug("   BEGIN  SELECT tiles.update_facilities_tiles() as result");
            res = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, new StatementAndParams("SELECT tiles.update_facilities_tiles() as result"));
            log.debug(getResult(res));
            log.debug("   END  SELECT tiles.update_facilities_tiles() as result");
            Double jrqFacilities = this.cmdJobRequest.createJobRequest(conn, "NTIS_FACILITIES_CLEAR_CACHE", null, Languages.LT, null);
            conn.commit();
            this.executorJob.execute(jrqFacilities);

            log.debug("   BEGIN  SELECT tiles.update_building_tiles as result");
            res = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, new StatementAndParams("SELECT tiles.update_building_tiles() as result"));
            log.debug(getResult(res));
            log.debug("   END  SELECT tiles.update_building_tiles as result");
            Double jrqBuilding = this.cmdJobRequest.createJobRequest(conn, "NTIS_MAP_OBJECTS_CLEAR_CACHE", null, Languages.LT, null);
            conn.commit();
            this.executorJob.execute(jrqBuilding);

            log.debug("   BEGIN  SELECT tiles.update_cent_management as result");
            res = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, new StatementAndParams("SELECT tiles.update_cent_management() as result"));
            log.debug(getResult(res));
            log.debug("   END  SELECT tiles.update_cent_management as result");
            conn.commit();
        } else {
            log.info("NO ACTIONS");
        }

        log.info("FINISH UPDATE_MAP_OBJECTS.");
    }

    private static String getResult(List<HashMap<String, String>> res) {
        return res.get(0).get("result");
    }

}
