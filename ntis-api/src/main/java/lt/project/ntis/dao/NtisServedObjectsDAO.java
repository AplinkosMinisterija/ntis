package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisServedObjectsDAOGen;
import java.util.Date;

public class NtisServedObjectsDAO extends NtisServedObjectsDAOGen {
   public NtisServedObjectsDAO() {
      super();
   }
   public NtisServedObjectsDAO(Double so_id, Double so_coordinate_latitude, Double so_coordinate_longitude, Double so_ad_id, Double so_wtf_id, Double so_bn_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05, Date so_date_from, Date so_date_to) {
      super(so_id, so_coordinate_latitude, so_coordinate_longitude, so_ad_id, so_wtf_id, so_bn_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05, so_date_from, so_date_to);
   }
}