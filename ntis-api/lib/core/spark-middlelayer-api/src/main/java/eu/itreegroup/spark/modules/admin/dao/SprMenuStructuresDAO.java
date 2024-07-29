package eu.itreegroup.spark.modules.admin.dao;

import java.util.Date;

import eu.itreegroup.spark.modules.admin.dao.gen.SprMenuStructuresDAOGen;

public class SprMenuStructuresDAO extends SprMenuStructuresDAOGen {
   public SprMenuStructuresDAO() {
      super();
   }
   public SprMenuStructuresDAO(Double mst_id, String mst_site, String mst_lang, String mst_code, String mst_title, String mst_icon, Double mst_order, String mst_uri, String mst_is_public, Date mst_date_from, Date mst_date_to, Double mst_frm_id, Double mst_mst_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(mst_id, mst_site, mst_lang, mst_code, mst_title, mst_icon, mst_order, mst_uri, mst_is_public, mst_date_from, mst_date_to, mst_frm_id, mst_mst_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}