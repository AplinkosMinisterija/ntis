package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisSelectedFacilitiesDAOGen;
import java.util.Date;

public class NtisSelectedFacilitiesDAO extends NtisSelectedFacilitiesDAOGen {
   public NtisSelectedFacilitiesDAO() {
      super();
   }
   public NtisSelectedFacilitiesDAO(Double fs_id, Double fs_usr_id, Double fs_org_id, Double fs_wtf_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(fs_id, fs_usr_id, fs_org_id, fs_wtf_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}