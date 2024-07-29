package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestExecutionsDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprJobRequestExecutionsDBServiceGen;

@Service
public class SprJobRequestExecutionsDBServiceImpl extends SprJobRequestExecutionsDBServiceGen implements SprJobRequestExecutionsDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprJobRequestExecutionsDBServiceImpl.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    public SprJobRequestExecutionsDBServiceImpl() {
    }

    @Override
    public SprJobRequestExecutionsDAO newRecord() {
        SprJobRequestExecutionsDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public void deleteRecordsByParent(Connection conn, Double identifier) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("DELETE FROM SPR_JOB_REQUEST_EXECUTIONS");
        stmt.addParam4WherePart(" JRE_JRQ_ID = ?::int ", identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }
}