package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisContractCommentsDAOGen;
import java.util.Date;

public class NtisContractCommentsDAO extends NtisContractCommentsDAOGen {
   public NtisContractCommentsDAO() {
      super();
   }
   public NtisContractCommentsDAO(Double cc_id, String cc_message, Date cc_created, Double cc_cot_id, Double cc_org_id, Double cc_per_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(cc_id, cc_message, cc_created, cc_cot_id, cc_org_id, cc_per_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}