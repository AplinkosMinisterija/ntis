package eu.itreegroup.spark.modules.admin.dao;

import java.util.Date;

import eu.itreegroup.spark.modules.admin.dao.gen.SprTemplatesDAOGen;

public class SprTemplatesDAO extends SprTemplatesDAOGen {
   public SprTemplatesDAO() {
      super();
   }
   public SprTemplatesDAO(Double tml_id, String tml_type, String tml_code, String tml_status, String tml_name, String tml_description, String tml_logic_impl, Date tml_date_from, Date tml_date_to, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(tml_id, tml_type, tml_code, tml_status, tml_name, tml_description, tml_logic_impl, tml_date_from, tml_date_to, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}