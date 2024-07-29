package eu.itreegroup.spark.modules.admin.dao;

import java.util.Date;

import eu.itreegroup.spark.modules.admin.dao.gen.SprProcessRequestsDAOGen;

public class SprProcessRequestsDAO extends SprProcessRequestsDAOGen {
   public SprProcessRequestsDAO() {
      super();
   }
   public SprProcessRequestsDAO(Double prq_id, String prq_type, Double prq_reference_id, String prq_initiated_by_system, String prq_token, String prq_lang, Date prq_date_from, Date prq_date_to, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(prq_id, prq_type, prq_reference_id, prq_initiated_by_system, prq_token, prq_lang, prq_date_from, prq_date_to, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}