package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprTemplateTextsDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprTemplateTextsDBServiceGen;

@Service
public class SprTemplateTextsDBServiceImpl extends SprTemplateTextsDBServiceGen implements SprTemplateTextsDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprTemplateTextsDBServiceImpl.class);

    @Autowired
    BaseControllerJDBC queryController;

    public SprTemplateTextsDBServiceImpl() {
    }

    @Override
    public SprTemplateTextsDAO newRecord() {
        SprTemplateTextsDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public ArrayList<SprTemplateTextsDAO> loadRecordsByParent(Connection conn, Double identifier) throws Exception {
        return (ArrayList<SprTemplateTextsDAO>) this.loadRecordsByParams(conn, " WHERE TMT_TML_ID = ? ", new SelectParamValue(identifier));
    }

    @Override
    public void deleteRecordsByParent(Connection conn, Double identifier) throws Exception {
        String executionsStmt = "DELETE FROM SPR_TEMPLATE_TEXTS WHERE TMT_TML_ID=?";
        log.debug("delete stmt: " + executionsStmt);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(executionsStmt);
        stmt.addSelectParam(identifier);
        queryController.adjustRecordsInDB(conn, stmt);
    }

}