package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisAdrSeniunijosDAOGen;
import java.util.Date;

public class NtisAdrSeniunijosDAO extends NtisAdrSeniunijosDAOGen {
   public NtisAdrSeniunijosDAO() {
      super();
   }
   public NtisAdrSeniunijosDAO(Double sen_id, String sen_code, String sen_name, String sen_municipality_code, Date sen_date_from, Date sen_date_to, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(sen_id, sen_code, sen_name, sen_municipality_code, sen_date_from, sen_date_to, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}