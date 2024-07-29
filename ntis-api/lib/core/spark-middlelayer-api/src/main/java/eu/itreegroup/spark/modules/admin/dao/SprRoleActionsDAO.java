package eu.itreegroup.spark.modules.admin.dao;

import java.util.Date;

import eu.itreegroup.spark.modules.admin.dao.gen.SprRoleActionsDAOGen;

public class SprRoleActionsDAO extends SprRoleActionsDAOGen {
   public SprRoleActionsDAO() {
      super();
   }
   public SprRoleActionsDAO(Double roa_id, String roa_type, Date roa_date_from, Date roa_date_to, String roa_default_menu_item, Double roa_rol_id, Double roa_mst_id, Double roa_fra_id, Double roa_frm_id, Double roa_assigned_rol_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(roa_id, roa_type, roa_date_from, roa_date_to, roa_default_menu_item, roa_rol_id, roa_mst_id, roa_fra_id, roa_frm_id, roa_assigned_rol_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
   
}