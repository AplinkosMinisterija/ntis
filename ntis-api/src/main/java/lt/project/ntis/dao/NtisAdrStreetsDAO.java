package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisAdrStreetsDAOGen;
import java.util.Date;

public class NtisAdrStreetsDAO extends NtisAdrStreetsDAOGen {
   public NtisAdrStreetsDAO() {
      super();
   }
   public NtisAdrStreetsDAO(Double str_id, Double str_street_code, String str_type, String str_type_abbreviation, String str_name, Double str_residence_code, Date str_date_from, Date str_date_to, Double str_re_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(str_id, str_street_code, str_type, str_type_abbreviation, str_name, str_residence_code, str_date_from, str_date_to, str_re_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}