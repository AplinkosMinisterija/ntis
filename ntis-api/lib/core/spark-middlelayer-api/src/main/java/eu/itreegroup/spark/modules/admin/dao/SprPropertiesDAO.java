package eu.itreegroup.spark.modules.admin.dao;

import eu.itreegroup.spark.modules.admin.dao.gen.SprPropertiesDAOGen;
import java.util.Date;

public class SprPropertiesDAO extends SprPropertiesDAOGen {
   public SprPropertiesDAO() {
      super();
   }
   public SprPropertiesDAO(Double prp_id, String prp_name, String prp_description, String prp_type, String prp_value, Double prp_fil_id, String prp_guid, String prp_install_instance, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(prp_id, prp_name, prp_description, prp_type, prp_value, prp_fil_id, prp_guid, prp_install_instance, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}