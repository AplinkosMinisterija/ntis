package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisCwFileDataErrsDAOGen;
import java.util.Date;

public class NtisCwFileDataErrsDAO extends NtisCwFileDataErrsDAOGen {
   public NtisCwFileDataErrsDAO() {
      super();
   }
   public NtisCwFileDataErrsDAO(Double cwfde_id, String cwfde_type, String cwfde_level, Double cwfde_rec_nr, Double cwfde_column_nr, String cwfde_column_name, String cwfde_column_value, String cwfde_msg_code, String cwfde_msg_text, Double cwfde_cwf_id, Double cwfde_cwfd_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(cwfde_id, cwfde_type, cwfde_level, cwfde_rec_nr, cwfde_column_nr, cwfde_column_name, cwfde_column_value, cwfde_msg_code, cwfde_msg_text, cwfde_cwf_id, cwfde_cwfd_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}