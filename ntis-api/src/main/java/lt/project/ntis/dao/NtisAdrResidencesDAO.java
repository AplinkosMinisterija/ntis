package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisAdrResidencesDAOGen;
import java.util.Date;

public class NtisAdrResidencesDAO extends NtisAdrResidencesDAOGen {
   public NtisAdrResidencesDAO() {
      super();
   }
   public NtisAdrResidencesDAO(Double re_id, Double re_recidence_code, String re_type, String re_type_abbreviation, String re_name_k, String re_name, Double re_sen_code, Double re_municipality_code, Date re_date_from, Date re_date_to, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(re_id, re_recidence_code, re_type, re_type_abbreviation, re_name_k, re_name, re_sen_code, re_municipality_code, re_date_from, re_date_to, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}