package eu.itreegroup.spark.modules.admin.dao;

import eu.itreegroup.spark.modules.admin.dao.gen.SprNewsCommentsDAOGen;
import java.util.Date;

public class SprNewsCommentsDAO extends SprNewsCommentsDAOGen {
   public SprNewsCommentsDAO() {
      super();
   }
   public SprNewsCommentsDAO(Double nwc_id, String nwc_comment, Date nwc_create_date, Double nwc_usr_id, Double nwc_nw_id, Double nwc_nwc_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(nwc_id, nwc_comment, nwc_create_date, nwc_usr_id, nwc_nw_id, nwc_nwc_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}