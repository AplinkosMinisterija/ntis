package eu.itreegroup.spark.modules.admin.dao;

import eu.itreegroup.spark.modules.admin.dao.gen.SprJobDefinitionsDAOGen;
import java.util.Date;

public class SprJobDefinitionsDAO extends SprJobDefinitionsDAOGen {
   public SprJobDefinitionsDAO() {
      super();
   }
   public SprJobDefinitionsDAO(Double jde_id, String jde_system, String jde_name, String jde_type, String jde_code, String jde_status, String jde_path, Double jde_tml_id, String jde_default_executer, String jde_execution_parameter, String jde_execution_unit, String jde_action_type, String jde_default_output_type, Date jde_last_action_time, Date jde_next_action_time, String jde_description, String jde_period, Double jde_week_day, Double jde_month_day, Double jde_year_day, Double jde_hour, Double jde_minutes, Double jde_period_in_minutes, Date jde_date_from, Date jde_date_to, Double jde_days_in_history, Double jde_days_in_request, String jde_adjust_to_current_date, String jde_ntf_on_completeion, Double jde_ntf_tml_id, String jde_ntf_tml_tmt_code, Double jde_email_tml_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(jde_id, jde_system, jde_name, jde_type, jde_code, jde_status, jde_path, jde_tml_id, jde_default_executer, jde_execution_parameter, jde_execution_unit, jde_action_type, jde_default_output_type, jde_last_action_time, jde_next_action_time, jde_description, jde_period, jde_week_day, jde_month_day, jde_year_day, jde_hour, jde_minutes, jde_period_in_minutes, jde_date_from, jde_date_to, jde_days_in_history, jde_days_in_request, jde_adjust_to_current_date, jde_ntf_on_completeion, jde_ntf_tml_id, jde_ntf_tml_tmt_code, jde_email_tml_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}