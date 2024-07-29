package lt.project.ntis.service;

import java.sql.Connection;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.dao.NtisServedObjectsDAO;
import lt.project.ntis.dao.NtisServedObjectsVersionDAO;
import lt.project.ntis.service.gen.NtisServedObjectsVersionDBServiceGen;

@Service
public class NtisServedObjectsVersionDBService extends NtisServedObjectsVersionDBServiceGen {

    @Autowired
    BaseControllerJDBC queryController;

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisServedObjectsVersionDBService.class);

    public NtisServedObjectsVersionDBService() {
    }

    @Override
    public NtisServedObjectsVersionDAO newRecord() {
        NtisServedObjectsVersionDAO daoObject = super.newRecord();
        return daoObject;
    }

    public void deleteAllRecords(Connection conn, Double wtfId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                DELETE
                  FROM ntis_served_objects_version
                  WHERE sov_wtf_id = ?::int
                  and sov_fua_id in (select fua_id
                                  from ntis_facility_update_agreement
                                  where fua_wtf_id = sov_wtf_id
                                    and fua_state = 'SUBMITTED')
                """);
        stmt.addSelectParam(wtfId);
        queryController.adjustRecordsInDB(conn, stmt);
    }

    public ArrayList<NtisServedObjectsDAO> getRecordByWtfFauId(Connection conn, Double sovId, Double fuaId) throws Exception {

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                 select so_id,
                         sov_geom as so_geom,
                         sov_coordinate_longitude as so_coordinate_longitude,
                         sov_coordinate_latitude  as so_coordinate_latitude,
                         sov_wtf_id as so_wtf_id,
                         sov_ad_id  as so_ad_id,
                         sov_bn_id as so_bn_id,
                         sov_fua_id
                    from ntis_served_objects_version
                    left join ntis_served_objects on so_ad_id = sov_ad_id and so_bn_id = sov_bn_id and so_wtf_id = sov_wtf_id
                  WHERE sov_wtf_id = ?::int
                   and sov_fua_id = ?::int
                """);
        stmt.addSelectParam(sovId);
        stmt.addSelectParam(fuaId);
        return (ArrayList<NtisServedObjectsDAO>) queryController.selectQueryAsObjectArrayList(conn, stmt, NtisServedObjectsDAO.class);
    }

}