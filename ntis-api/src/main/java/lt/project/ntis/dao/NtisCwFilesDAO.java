package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisCwFilesDAOGen;
import java.util.Date;

public class NtisCwFilesDAO extends NtisCwFilesDAOGen {
   public NtisCwFilesDAO() {
      super();
   }
   public NtisCwFilesDAO(Double cwf_id, Date cwf_import_date, String cwf_status, Date cwf_status_date, Double cwf_org_id, Double cwf_fil_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05, Double cwf_usr_id) {
      super(cwf_id, cwf_import_date, cwf_status, cwf_status_date, cwf_org_id, cwf_fil_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05, cwf_usr_id);
   }
}