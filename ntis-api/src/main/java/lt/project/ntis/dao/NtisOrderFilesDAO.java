package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisOrderFilesDAOGen;
import java.util.Date;

public class NtisOrderFilesDAO extends NtisOrderFilesDAOGen {
   public NtisOrderFilesDAO() {
      super();
   }
   public NtisOrderFilesDAO(Double orf_id, Date orf_import_date, String orf_status, Date orf_status_date, Double orf_org_id, Double orf_fil_id, Double orf_srv_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05, Double orf_usr_id) {
      super(orf_id, orf_import_date, orf_status, orf_status_date, orf_org_id, orf_fil_id, orf_srv_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05, orf_usr_id);
   }
}