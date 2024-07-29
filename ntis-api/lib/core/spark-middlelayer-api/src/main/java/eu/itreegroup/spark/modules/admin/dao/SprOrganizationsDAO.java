package eu.itreegroup.spark.modules.admin.dao;

import eu.itreegroup.spark.modules.admin.dao.gen.SprOrganizationsDAOGen;
import java.util.Date;

public class SprOrganizationsDAO extends SprOrganizationsDAOGen {
   public SprOrganizationsDAO() {
      super();
   }
   public SprOrganizationsDAO(Double org_id, String org_name, String org_code, String org_type, String org_region, String org_phone, String org_email, String org_website, String org_address, String org_house_number, String org_bank_account, String org_bank_name, String org_delegation_person, String org_delegation_person_position, Date org_date_from, Date org_date_to, Double org_org_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(org_id, org_name, org_code, org_type, org_region, org_phone, org_email, org_website, org_address, org_house_number, org_bank_account, org_bank_name, org_delegation_person, org_delegation_person_position, org_date_from, org_date_to, org_org_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}