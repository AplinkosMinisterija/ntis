package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprFormActionsDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprFormActionsDBServiceGen;

@Service
public class SprFormActionsDBServiceImpl extends SprFormActionsDBServiceGen implements SprFormActionsDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprFormActionsDBServiceImpl.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    public SprFormActionsDBServiceImpl() {
    }

    @Override
    public SprFormActionsDAO newRecord() {
        SprFormActionsDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public SprFormActionsDAO loadRecordByFormIdAndCode(Connection conn, Double formId, String actionCode) throws Exception {
        return this.loadRecordByParams(conn, " WHERE FRA_FRM_ID = ?::int and FRA_CODE = ? ", new SelectParamValue(formId), new SelectParamValue(actionCode));
    }

    @Override
    public void deleteActionsForForms(Connection conn, Double formId) throws Exception {
        String stmtStr = "DELETE FROM SPR_FORM_ACTIONS WHERE FRA_FRM_ID=?::int";
        log.debug("delete stmt: " + stmtStr);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(stmtStr);
        stmt.addSelectParam(formId);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }
}