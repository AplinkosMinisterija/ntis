package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisAdrStatsDAOGen;
import java.util.Date;

public class NtisAdrStatsDAO extends NtisAdrStatsDAOGen {
   public NtisAdrStatsDAO() {
      super();
   }
   public NtisAdrStatsDAO(Double ads_id, Double ads_municipality_code, Double ads_aob_code, Double ads_residence_code, Double ads_street_code, Double ads_coordinate_latitude, Double ads_coordinate_longitude, String ads_nr, String ads_housing_nr, String ads_post_code, Date ads_date_from, Date ads_date_to, Double ads_re_id, Double ads_str_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(ads_id, ads_municipality_code, ads_aob_code, ads_residence_code, ads_street_code, ads_coordinate_latitude, ads_coordinate_longitude, ads_nr, ads_housing_nr, ads_post_code, ads_date_from, ads_date_to, ads_re_id, ads_str_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}