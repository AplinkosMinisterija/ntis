package lt.project.ntis.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.SelectParamValue;
import lt.project.ntis.dao.NtisServiceRequestsDAO;
import lt.project.ntis.service.gen.NtisServiceRequestsDBServiceGen;

@Service
public class NtisServiceRequestsDBService extends NtisServiceRequestsDBServiceGen {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisServiceRequestsDBService.class);

    public NtisServiceRequestsDBService() {
    }

    @Override
    public NtisServiceRequestsDAO newRecord() {
        NtisServiceRequestsDAO daoObject = super.newRecord();
        return daoObject;
    }

    public NtisServiceRequestsDAO loadRecordByIdAndOrgId(Connection conn, Double identifier, Double orgId) throws Exception {
        return this.loadRecordByParams(conn, " where sr_id = ?::int and sr_org_id = ?::int ", new SelectParamValue(identifier), new SelectParamValue(orgId));
    }
}