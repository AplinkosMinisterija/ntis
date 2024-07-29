package eu.itreegroup.spark.modules.admin.dao;

import eu.itreegroup.spark.modules.admin.dao.gen.SprAuditableTablesDAOGen;
import java.util.Date;

public class SprAuditableTablesDAO extends SprAuditableTablesDAOGen {
   public SprAuditableTablesDAO() {
      super();
   }
   public SprAuditableTablesDAO(Double aut_id, String aut_table_schema, String aut_table_name, String aut_trigger_enabled, String aut_trigger_name, Double aut_num_of_days_in_audit, Double aut_num_of_days_in_archive, String aut_audit_table_name, String aut_archive_table_name, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(aut_id, aut_table_schema, aut_table_name, aut_trigger_enabled, aut_trigger_name, aut_num_of_days_in_audit, aut_num_of_days_in_archive, aut_audit_table_name, aut_archive_table_name, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}