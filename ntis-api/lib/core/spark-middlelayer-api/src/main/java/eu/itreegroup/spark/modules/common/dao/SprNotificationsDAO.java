package eu.itreegroup.spark.modules.common.dao;

import eu.itreegroup.spark.modules.common.dao.gen.SprNotificationsDAOGen;
import java.util.Date;

public class SprNotificationsDAO extends SprNotificationsDAOGen {
   public SprNotificationsDAO() {
      super();
   }
   public SprNotificationsDAO(Double ntf_id, String ntf_type, Double ntf_reference, String ntf_title, String ntf_message, Date ntf_creation_date, Date ntf_mark_as_read_date, Double ntf_usr_id, Double ntf_rol_id, Double ntf_org_id, Date ntf_date_from, Date ntf_date_to, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(ntf_id, ntf_type, ntf_reference, ntf_title, ntf_message, ntf_creation_date, ntf_mark_as_read_date, ntf_usr_id, ntf_rol_id, ntf_org_id, ntf_date_from, ntf_date_to, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}