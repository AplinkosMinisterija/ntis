package lt.project.ntis.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.job.request.impl.CmdJobRequest;
import eu.itreegroup.spark.app.scheduler.ExecutorJob;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;

@Service
public class TilesDBService {

    private static final Logger log = LoggerFactory.getLogger(TilesDBService.class);

    @Autowired
    CmdJobRequest cmdJobRequest;

    @Autowired
    ExecutorJob executorJob;

    @Autowired
    BaseControllerJDBC queryController;

    public void updateOneWastewater(Connection conn, Double wtfId, TilesAction action) throws Exception {
        StatementAndParams statementAndParams = new StatementAndParams("""
                SELECT tiles.update_one_wastewater(?::int, ?::character) as result
                """);
        statementAndParams.addSelectParam(wtfId);
        statementAndParams.addSelectParam(action.getActionCode());
        List<HashMap<String, String>> res = this.queryController.selectQueryAsDataArrayList(conn, statementAndParams);
        log.debug("UpdateOneWastewater (wtfId:{}, action:{}) result - {}.", wtfId, action.getActionCode(), getResult(res));
    }

    public void updateOneBuilding(Connection conn, Double bnId, TilesAction action) throws Exception {
        StatementAndParams statementAndParams = new StatementAndParams("""
                SELECT tiles.update_one_building(?::int, ?::character) as result
                """);
        statementAndParams.addSelectParam(bnId);
        statementAndParams.addSelectParam(action.getActionCode());
        List<HashMap<String, String>> res = this.queryController.selectQueryAsDataArrayList(conn, statementAndParams);
        log.debug("UpdateOneBuilding (bnId:{}, action:{}) result - {}.", bnId, action.getActionCode(), getResult(res));
    }

    private static String getResult(List<HashMap<String, String>> res) {
        return res.get(0).get("result");
    }

    public void cleanBuildingsCache(Connection conn) throws Exception {
        Double jrqBuilding = this.cmdJobRequest.createJobRequest(conn, "NTIS_MAP_OBJECTS_CLEAR_CACHE", null, Languages.LT, null);
        conn.commit();
        this.executorJob.execute(jrqBuilding);

    }

    public void cleanWastewaterCache(Connection conn) throws Exception {
        Double jrqDischarges = this.cmdJobRequest.createJobRequest(conn, "NTIS_DISCHARGES_CLEAR_CACHE", null, Languages.LT, null);
        conn.commit();
        this.executorJob.execute(jrqDischarges);

        Double jrqFacilities = this.cmdJobRequest.createJobRequest(conn, "NTIS_FACILITIES_CLEAR_CACHE", null, Languages.LT, null);
        conn.commit();
        this.executorJob.execute(jrqFacilities);
    }

    /**
     * Duomenų bazės schemos 'tiles' funkcijose naudojami 'action' tipai.
     * 
     */
    public enum TilesAction {

        INSERT("I"), UPDATE("U"), DELETE("D");

        private String actionCode;

        private TilesAction(String actionCode) {
            this.actionCode = actionCode;
        }

        public String getActionCode() {
            return actionCode;
        }
    }

}
