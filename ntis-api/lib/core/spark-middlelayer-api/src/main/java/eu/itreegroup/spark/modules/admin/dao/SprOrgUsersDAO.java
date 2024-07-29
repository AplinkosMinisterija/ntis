package eu.itreegroup.spark.modules.admin.dao;

import eu.itreegroup.spark.modules.admin.dao.gen.SprOrgUsersDAOGen;
import java.util.Date;

public class SprOrgUsersDAO extends SprOrgUsersDAOGen {
   public SprOrgUsersDAO() {
      super();
   }
   public SprOrgUsersDAO(Double ou_id, String ou_position, String ou_profile_token, Date ou_date_from, Date ou_date_to, Double ou_usr_id, Double ou_org_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(ou_id, ou_position, ou_profile_token, ou_date_from, ou_date_to, ou_usr_id, ou_org_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}