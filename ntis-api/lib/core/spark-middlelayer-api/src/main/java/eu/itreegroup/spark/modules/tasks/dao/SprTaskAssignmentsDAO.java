package eu.itreegroup.spark.modules.tasks.dao;

import eu.itreegroup.spark.modules.tasks.dao.gen.SprTaskAssignmentsDAOGen;
import java.util.Date;

public class SprTaskAssignmentsDAO extends SprTaskAssignmentsDAOGen {
   public SprTaskAssignmentsDAO() {
      super();
   }
   public SprTaskAssignmentsDAO(Double tat_id, Double tat_usr_id, Double tat_org_id, Double tat_rol_id, Double tat_ins_id, Double tat_tas_id, Date tat_date_from, Date tat_date_to, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(tat_id, tat_usr_id, tat_org_id, tat_rol_id, tat_ins_id, tat_tas_id, tat_date_from, tat_date_to, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}