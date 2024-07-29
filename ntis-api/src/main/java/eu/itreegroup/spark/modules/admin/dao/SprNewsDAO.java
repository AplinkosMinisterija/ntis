package eu.itreegroup.spark.modules.admin.dao;

import eu.itreegroup.spark.modules.admin.dao.gen.SprNewsDAOGen;
import java.util.Date;

public class SprNewsDAO extends SprNewsDAOGen {
   public SprNewsDAO() {
      super();
   }
   public SprNewsDAO(Double nw_id, String nw_type, String nw_lang, String nw_title, String nw_summary, String nw_text, String nw_content_for_search, Date nw_publication_date, Date nw_date_from, Date nw_date_to, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(nw_id, nw_type, nw_lang, nw_title, nw_summary, nw_text, nw_content_for_search, nw_publication_date, nw_date_from, nw_date_to, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}