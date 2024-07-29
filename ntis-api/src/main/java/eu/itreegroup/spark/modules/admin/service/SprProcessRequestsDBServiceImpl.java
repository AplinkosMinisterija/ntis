package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.enums.ProcessRequestType;
import eu.itreegroup.spark.modules.admin.dao.SprProcessRequestsDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprProcessRequestsDBServiceGen;

@Service
public class SprProcessRequestsDBServiceImpl extends SprProcessRequestsDBServiceGen implements SprProcessRequestsDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprProcessRequestsDBServiceImpl.class);

    @Autowired
    DBStatementManager dBStatementManager;

    public SprProcessRequestsDBServiceImpl() {
    }

    @Override
    public SprProcessRequestsDAO newRecord() {
        SprProcessRequestsDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public SprProcessRequestsDAO loadByToken(Connection conn, ProcessRequestType requestType, String token) throws Exception {
        return this.loadRecordByParams(conn,
                " WHERE PRQ_TYPE = ? and PRQ_TOKEN = ? and " + dBStatementManager.getPeriodValidationForCurrentDateStr("PRQ_DATE_FROM", "PRQ_DATE_TO", false),
                new SelectParamValue(requestType.getCode()), new SelectParamValue(token));
    }
}