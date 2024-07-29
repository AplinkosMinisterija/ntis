package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisServiceRequestsDAOGen;
import java.util.Date;

public class NtisServiceRequestsDAO extends NtisServiceRequestsDAOGen {
   public NtisServiceRequestsDAO() {
      super();
   }
   public NtisServiceRequestsDAO(Double sr_id, String sr_reg_no, String sr_type, String sr_email, String sr_email_verified, String sr_phone, String sr_resp_person_description, String sr_homepage, String sr_data_is_correct, String sr_rules_accepted, String sr_status, Date sr_status_date, Date sr_registration_date, String sr_removal_reason, Date sr_removal_date, Double sr_org_id, Double sr_usr_id, Double sr_per_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(sr_id, sr_reg_no, sr_type, sr_email, sr_email_verified, sr_phone, sr_resp_person_description, sr_homepage, sr_data_is_correct, sr_rules_accepted, sr_status, sr_status_date, sr_registration_date, sr_removal_reason, sr_removal_date, sr_org_id, sr_usr_id, sr_per_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}