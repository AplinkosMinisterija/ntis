package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisServicesDAOGen;
import java.util.Date;

public class NtisServicesDAO extends NtisServicesDAOGen {
   public NtisServicesDAO() {
      super();
   }
   public NtisServicesDAO(Double srv_id, String srv_type, String srv_contract_available, String srv_available_in_ntis_portal, String srv_lithuanian_level, String srv_email, String srv_phone_no, Double srv_price_from, Double srv_price_to, Double srv_completion_in_days_from, Double srv_completion_in_days_to, String srv_description, Date srv_date_from, Date srv_date_to, Double srv_org_id, Double srv_fil_id, Double srv_lab_instr_fil_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(srv_id, srv_type, srv_contract_available, srv_available_in_ntis_portal, srv_lithuanian_level, srv_email, srv_phone_no, srv_price_from, srv_price_to, srv_completion_in_days_from, srv_completion_in_days_to, srv_description, srv_date_from, srv_date_to, srv_org_id, srv_fil_id, srv_lab_instr_fil_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}