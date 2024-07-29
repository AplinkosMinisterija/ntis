package lt.project.ntis.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.dao.NtisOrdersDAO;
import lt.project.ntis.service.gen.NtisOrdersDBServiceGen;

@Service
public class NtisOrdersDBService extends NtisOrdersDBServiceGen {

    @Autowired
    NtisServicesDBService ntisServicesDBService;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisOrdersDBService.class);

    public NtisOrdersDBService() {
    }

    @Override
    public NtisOrdersDAO newRecord() {
        NtisOrdersDAO daoObject = super.newRecord();
        return daoObject;
    }

    public NtisOrdersDAO loadRecordByIdAndOrgId(Connection conn, Double orgId, Double identifier) throws Exception {
        return this.loadRecordByParams(conn, " where ord_id = ?::int and ord_org_id = ?::int ", new SelectParamValue(identifier), new SelectParamValue(orgId));
    }

    public NtisOrdersDAO loadRecordByIdAndPerId(Connection conn, Double perId, Double identifier) throws Exception {
        return this.loadRecordByParams(conn, " where ord_id = ?::int and ord_per_id = ?::int ", new SelectParamValue(identifier), new SelectParamValue(perId));
    }

    public NtisOrdersDAO loadRecordByIdAndSrvOrgId(Connection conn, Double orgId, Double identifier) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT ORD_ID AS id " + //
                "FROM NTIS_ORDERS ORD " + //
                "LEFT JOIN NTIS_SERVICES SRV ON SRV.SRV_ID = ORD.ORD_SRV_ID " + //
                "WHERE ORD_ID = ?::int AND SRV.SRV_ORG_ID = ?::int ");
        stmt.addSelectParam(identifier);
        stmt.addSelectParam(orgId);
        return this.loadRecord(conn, baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, RecordIdentifier.class).get(0).getIdAsDouble());
    }
}