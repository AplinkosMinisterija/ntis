package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestArgsDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprJobRequestArgsDBServiceGen;

@Service
public class SprJobRequestArgsDBServiceImpl extends SprJobRequestArgsDBServiceGen implements SprJobRequestArgsDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprJobRequestArgsDBServiceImpl.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    public SprJobRequestArgsDBServiceImpl() {
    }

    @Override
    public SprJobRequestArgsDAO newRecord() {
        SprJobRequestArgsDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public void deleteRecordsByParent(Connection conn, Double identifier) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("DELETE FROM SPR_JOB_REQUEST_ARGS");
        stmt.addParam4WherePart("JRA_JRQ_ID = ?::int ", identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);

    }

    @Override
    public Map<String, String> getArgsMap(Connection conn, Double jrqId) throws Exception {
        HashMap<String, String> result = new HashMap<String, String>();
        StatementAndParams argsStatement = new StatementAndParams("" //
                + "SELECT jra_name, " //
                + "       jra_value " //
                + "  FROM spark.spr_job_request_args " //
                + " WHERE jra_jrq_id = ?");
        argsStatement.addSelectParam(jrqId);
        List<HashMap<String, String>> argsResults = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, argsStatement);
        if (argsResults != null && !argsResults.isEmpty()) {
            for (HashMap<String, String> argsResult : argsResults) {
                result.put(argsResult.get("jra_name"), argsResult.get("jra_value"));
            }
        }
        return result;
    }
}
