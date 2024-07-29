package lt.project.ntis.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.dao.NtisDeliveryFacilitiesDAO;
import lt.project.ntis.service.gen.NtisDeliveryFacilitiesDBServiceGen;

@Service
public class NtisDeliveryFacilitiesDBService extends NtisDeliveryFacilitiesDBServiceGen {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisDeliveryFacilitiesDBService.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    public NtisDeliveryFacilitiesDBService() {
    }

    @Override
    public NtisDeliveryFacilitiesDAO newRecord() {
        NtisDeliveryFacilitiesDAO daoObject = super.newRecord();
        return daoObject;
    }

    public void deleteRecordByWdId(Connection conn, Double identifier) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("DELETE FROM NTIS_DELIVERY_FACILITIES WHERE DF_WD_ID=?::int ");
        stmt.setWhereExists(true);
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }

}