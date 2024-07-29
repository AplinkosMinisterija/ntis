package lt.project.ntis.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.SelectParamValue;
import lt.project.ntis.dao.NtisServiceReqItemsDAO;
import lt.project.ntis.service.gen.NtisServiceReqItemsDBServiceGen;

@Service
public class NtisServiceReqItemsDBService extends NtisServiceReqItemsDBServiceGen {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisServiceReqItemsDBService.class);

    public NtisServiceReqItemsDBService() {
    }

    @Override
    public NtisServiceReqItemsDAO newRecord() {
        NtisServiceReqItemsDAO daoObject = super.newRecord();
        return daoObject;
    }

    public NtisServiceReqItemsDAO loadRecordBySrvId(Connection conn, Double identifier) throws Exception {
        return this.loadRecordByParams(conn, " where sri_srv_id = ?::int ", new SelectParamValue(identifier));
    }

}