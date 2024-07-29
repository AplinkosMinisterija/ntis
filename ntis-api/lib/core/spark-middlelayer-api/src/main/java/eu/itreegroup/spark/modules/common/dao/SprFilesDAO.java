package eu.itreegroup.spark.modules.common.dao;

import java.util.Date;

import eu.itreegroup.spark.modules.common.dao.gen.SprFilesDAOGen;

public class SprFilesDAO extends SprFilesDAOGen {
   public SprFilesDAO() {
      super();
   }
   public SprFilesDAO(Double fil_id, String fil_path, String fil_server, String fil_content_type, String fil_key, String fil_name, Double fil_size, String fil_status, Date fil_status_date, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(fil_id, fil_path, fil_server, fil_content_type, fil_key, fil_name, fil_size, fil_status, fil_status_date, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}