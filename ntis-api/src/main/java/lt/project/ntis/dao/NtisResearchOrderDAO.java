package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisResearchOrderDAOGen;
import java.util.Date;

public class NtisResearchOrderDAO extends NtisResearchOrderDAOGen {
   public NtisResearchOrderDAO() {
      super();
   }
   public NtisResearchOrderDAO(Double ro_id, String ro_additional_description, String ro_date_from, String ro_created_in_ntis_portal, String ro_phone_number, String ro_rejection_reason, String ro_compliance_norms, String ro_state, String ro_created, String ro_email, String ro_date_to, Double ro_wtf_id, Double ro_org_id, Double ro_fil_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(ro_id, ro_additional_description, ro_date_from, ro_created_in_ntis_portal, ro_phone_number, ro_rejection_reason, ro_compliance_norms, ro_state, ro_created, ro_email, ro_date_to, ro_wtf_id, ro_org_id, ro_fil_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}