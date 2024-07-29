package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisOrderFileDataErrsDAOGen;
import java.util.Date;

public class NtisOrderFileDataErrsDAO extends NtisOrderFileDataErrsDAOGen {
   public NtisOrderFileDataErrsDAO() {
      super();
   }
   public NtisOrderFileDataErrsDAO(Double orfde_id, String orfde_type, String orfde_level, Double orfde_rec_nr, Double orfde_column_nr, String orfde_column_name, String orfde_column_value, String orfde_msg_code, String orfde_msg_text, Double orfde_orf_id, Double orfde_orfd_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(orfde_id, orfde_type, orfde_level, orfde_rec_nr, orfde_column_nr, orfde_column_name, orfde_column_value, orfde_msg_code, orfde_msg_text, orfde_orf_id, orfde_orfd_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}