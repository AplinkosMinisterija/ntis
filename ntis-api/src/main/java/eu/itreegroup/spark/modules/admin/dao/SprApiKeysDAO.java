package eu.itreegroup.spark.modules.admin.dao;

import eu.itreegroup.spark.modules.admin.dao.gen.SprApiKeysDAOGen;
import java.util.Date;

public class SprApiKeysDAO extends SprApiKeysDAOGen {
   public SprApiKeysDAO() {
      super();
   }
   public SprApiKeysDAO(Double api_id, String api_type_code, String api_key, Double api_ou_id, Double api_usr_id, Date api_date_from, Date api_date_to, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(api_id, api_type_code, api_key, api_ou_id, api_usr_id, api_date_from, api_date_to, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}