package eu.itreegroup.spark.modules.admin.dao;

import eu.itreegroup.spark.modules.admin.dao.gen.SprJobRequestsDAOGen;
import java.util.Date;

public class SprJobRequestsDAO extends SprJobRequestsDAOGen {
   public SprJobRequestsDAO() {
      super();
   }
   public SprJobRequestsDAO(Double jrq_id, String jrq_status, String jrq_type, Double jrq_fil_id, String jrq_host_created, Double jrq_jde_id, Double jrq_usr_id, String jrq_executer_name, Date jrq_request_time, Date jrq_result_time, Date jrq_start_date, Date jrq_end_date, String jrq_result_type, String jrq_error, String jrq_priority, String jrq_data, String jrq_lang, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(jrq_id, jrq_status, jrq_type, jrq_fil_id, jrq_host_created, jrq_jde_id, jrq_usr_id, jrq_executer_name, jrq_request_time, jrq_result_time, jrq_start_date, jrq_end_date, jrq_result_type, jrq_error, jrq_priority, jrq_data, jrq_lang, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}