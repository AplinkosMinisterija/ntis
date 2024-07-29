package lt.project.ntis.dao;

import java.util.Date;

import lt.project.ntis.dao.gen.NtisServedObjectsVersionDAOGen;

public class NtisServedObjectsVersionDAO extends NtisServedObjectsVersionDAOGen {

    public NtisServedObjectsVersionDAO() {
        super();
    }

    public NtisServedObjectsVersionDAO(Double sov_id, String sov_geom, Double sov_coordinate_latitude, Double sov_coordinate_longitude, Double sov_wtf_id,
            Double sov_ad_id, Double sov_bn_id, Double sov_fua_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp,
            Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02,
            Date d03, Date d04, Date d05) {
        super(sov_id, sov_geom, sov_coordinate_latitude, sov_coordinate_longitude, sov_wtf_id, sov_ad_id, sov_bn_id, sov_fua_id, rec_version,
                rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
    }

}