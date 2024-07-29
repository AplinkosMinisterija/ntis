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
import lt.project.ntis.dao.NtisWastewaterDeliveriesDAO;
import lt.project.ntis.service.gen.NtisWastewaterDeliveriesDBServiceGen;

@Service
public class NtisWastewaterDeliveriesDBService extends NtisWastewaterDeliveriesDBServiceGen {

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisWastewaterDeliveriesDBService.class);

    public NtisWastewaterDeliveriesDBService() {
    }

    @Override
    public NtisWastewaterDeliveriesDAO newRecord() {
        NtisWastewaterDeliveriesDAO daoObject = super.newRecord();
        return daoObject;
    }

    public NtisWastewaterDeliveriesDAO loadRecordByIdAndOrgId(Connection conn, Double orgId, Double identifier) throws Exception {
        return this.loadRecordByParams(conn, " where wd_id = ?::int and wd_org_id = ?::int ", new SelectParamValue(identifier), new SelectParamValue(orgId));
    }

    /**
     * Metodas užkraus NtisWastewaterDeliveriesDAO įrašą pagal nurodytą vandentvarkos organizacijos ID ir pristatymo ID tuo atveju, 
     *  jeigu nurodyto pristatymo (wd_id) valyklos organizacijos ID (wto_org_id) sutaps su nurodytu vandentvarkos org ID 
     * @param conn - prisijungimas prie DB
     * @param orgId - vandentvarkos įmonės ID
     * @param identifier - nuotekų pristatymo ID
     * @return NtisWastewaterDeliveriesDAO objektas
     * @throws Exception
     */
    public NtisWastewaterDeliveriesDAO loadRecordByIdAndWtoOrgId(Connection conn, Double orgId, Double identifier) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" SELECT WD_ID AS id " + //
                " FROM NTIS_WASTEWATER_DELIVERIES WD " + //
                " JOIN NTIS_WASTEWATER_TREATMENT_ORG WTO ON WD.WD_WTO_ID = WTO.WTO_ID " + //
                " WHERE WD_ID = ?::int AND WTO.WTO_ORG_ID = ?::int ");
        stmt.addSelectParam(identifier);
        stmt.addSelectParam(orgId);
        return this.loadRecord(conn, baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, RecordIdentifier.class).get(0).getIdAsDouble());
    }
}