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
 * Pilnas žemėlapių cache atnaujinimas.
 */

@Service("NTIS_MAP_OBJECTS_UPDATE_FULL")
public class UpdateMapObjectsFull extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(UpdateMapObjectsFull.class);

    @Autowired
    CmdJobRequest cmdJobRequest;

    @Autowired
    ExecutorJob executorJob;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception {
        log.info("START NTIS_MAP_OBJECTS_UPDATE_FULL.");
        List<HashMap<String, String>> res;

        log.debug("   BEGIN  SELECT tiles.update_wells_tiles() as result");
        res = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, new StatementAndParams("SELECT tiles.update_wells_tiles() as result"));
        log.debug(getResult(res));
        log.debug("   END  SELECT tiles.update_wells_tiles() as result");
        conn.commit();

        log.debug("   BEGIN  SELECT tiles.update_discharges_tiles() as result");
        res = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, new StatementAndParams("SELECT tiles.update_discharges_tiles() as result"));
        log.debug(getResult(res));
        log.debug("   END  SELECT tiles.update_discharges_tiles() as result");
        conn.commit();

        log.debug("   BEGIN  SELECT tiles.update_facilities_tiles() as result");
        res = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, new StatementAndParams("SELECT tiles.update_facilities_tiles() as result"));
        log.debug(getResult(res));
        log.debug("   END  SELECT tiles.update_facilities_tiles() as result");
        conn.commit();

        log.debug("   BEGIN  SELECT tiles.update_building_tiles as result");
        res = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, new StatementAndParams("SELECT tiles.update_building_tiles() as result"));
        log.debug(getResult(res));
        log.debug("   END  SELECT tiles.update_building_tiles as result");
        conn.commit();

        log.debug("   BEGIN  SELECT tiles.update_cent_management as result");
        res = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, new StatementAndParams("SELECT tiles.update_cent_management() as result"));
        log.debug(getResult(res));
        log.debug("   END  SELECT tiles.update_cent_management as result");
        conn.commit();

        log.debug("   BEGIN  SELECT tiles.update_disposal as result");
        res = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, new StatementAndParams("SELECT tiles.update_disposal() as result"));
        log.debug(getResult(res));
        log.debug("   END  SELECT tiles.update_disposal as result");
        conn.commit();

        log.debug("   BEGIN  SELECT tiles.update_research as result");
        res = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, new StatementAndParams("SELECT tiles.update_research() as result"));
        log.debug(getResult(res));
        log.debug("   END  SELECT tiles.update_research as result");
        conn.commit();

        Double jrqTegola = this.cmdJobRequest.createJobRequest(conn, "NTIS_TEGOLA_CLEAR_CACHE", null, Languages.LT, null);
        conn.commit();
        this.executorJob.execute(jrqTegola);

        log.info("FINISH NTIS_MAP_OBJECTS_UPDATE_FULL.");
    }

    private static String getResult(List<HashMap<String, String>> res) {
        return res.get(0).get("result");
    }

}
