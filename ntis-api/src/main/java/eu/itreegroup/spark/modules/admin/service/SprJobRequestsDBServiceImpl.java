package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprJobRequestsDBServiceGen;

@Service
public class SprJobRequestsDBServiceImpl extends SprJobRequestsDBServiceGen implements SprJobRequestsDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprJobRequestsDBServiceImpl.class);

    @Autowired
    BaseControllerJDBC queryController;

    public SprJobRequestsDBServiceImpl() {
    }

    @Override
    public SprJobRequestsDAO newRecord() {
        SprJobRequestsDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public ArrayList<SprJobRequestsDAO> loadActiveJobReqest(Connection conn, String executerName) throws Exception {
        return (ArrayList<SprJobRequestsDAO>) this.loadRecordsByParams(conn,
                " where jrq_status in ( 'WAITING') and (jrq_executer_name = ? or jrq_executer_name is null or jrq_executer_name = '')  order by 1 desc for update",
                new SelectParamValue(executerName));
    }

    @Override
    public SprJobRequestsDAO loadRecord4Update(Connection conn, Object identifier) throws Exception {
        return this.loadRecordByParams(conn, " WHERE JRQ_ID = ? FOR UPDATE  ", new SelectParamValue(Utils.getLong(identifier)));
    }

    @Override
    public ArrayList<SprJobRequestsDAO> loadActiveNotAssignedPrintReqest(Connection conn) throws Exception {
        return (ArrayList<SprJobRequestsDAO>) this.loadRecordsByParams(conn,
                " where jrq_status in ( 'WAITING') and jrq_executer_name is null  order by 1 desc");
    }

    @Override
    public void deleteRecordsAndChildrenByParent(Connection conn, Double identifier) throws Exception {
        String executionsStmt = "DELETE FROM spr_job_request_executions ex WHERE ex.jre_jrq_id IN (SELECT jrq_id FROM spr_job_requests rq WHERE rq.jrq_jde_id = ?)";
        log.debug("delete stmt: " + executionsStmt);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(executionsStmt);
        stmt.addSelectParam(identifier);
        queryController.adjustRecordsInDB(conn, stmt);

        String argsStmt = "DELETE FROM spr_job_request_args ar WHERE ar.jra_jrq_id IN (SELECT jrq_id FROM spr_job_requests rq WHERE rq.jrq_jde_id = ?)";
        log.debug("delete stmt: " + argsStmt);
        stmt = new StatementAndParams();
        stmt.setStatement(argsStmt);
        stmt.addSelectParam(identifier);
        queryController.adjustRecordsInDB(conn, stmt);

        String requestsStmt = "DELETE FROM spr_job_requests WHERE jrq_jde_id = ?";
        log.debug("delete stmt: " + requestsStmt);
        stmt = new StatementAndParams();
        stmt.setStatement(requestsStmt);
        stmt.addSelectParam(identifier);
        queryController.adjustRecordsInDB(conn, stmt);
    }
}