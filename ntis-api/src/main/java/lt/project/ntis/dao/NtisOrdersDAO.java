package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisOrdersDAOGen;
import java.util.Date;

public class NtisOrdersDAO extends NtisOrdersDAOGen {
   public NtisOrdersDAO() {
      super();
   }
   public NtisOrdersDAO(Double ord_id, String ord_additional_description, String ord_state, String ord_email, String ord_type, String ord_compliance_norms, String ord_phone_number, String ord_created_in_ntis_portal, String ord_rejection_reason, Date ord_rejection_date, Date ord_completion_request_date, Date ord_completion_estimate_date, String ord_planned_works_description, Date ord_created, Date ord_removed_sewage_date, Date ord_prefered_date_from, Date ord_prefered_date_to, Double ord_org_id, Double ord_per_id, Double ord_usr_id, Double ord_wtf_id, Double ord_srv_id, Double ord_cs_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(ord_id, ord_additional_description, ord_state, ord_email, ord_type, ord_compliance_norms, ord_phone_number, ord_created_in_ntis_portal, ord_rejection_reason, ord_rejection_date, ord_completion_request_date, ord_completion_estimate_date, ord_planned_works_description, ord_created, ord_removed_sewage_date, ord_prefered_date_from, ord_prefered_date_to, ord_org_id, ord_per_id, ord_usr_id, ord_wtf_id, ord_srv_id, ord_cs_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}