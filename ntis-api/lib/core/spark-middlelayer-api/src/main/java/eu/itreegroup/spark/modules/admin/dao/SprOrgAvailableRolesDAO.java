package eu.itreegroup.spark.modules.admin.dao;

import java.util.Date;

import eu.itreegroup.spark.modules.admin.dao.gen.SprOrgAvailableRolesDAOGen;

public class SprOrgAvailableRolesDAO extends SprOrgAvailableRolesDAOGen {
   public SprOrgAvailableRolesDAO() {
      super();
   }
   public SprOrgAvailableRolesDAO(Double oar_id, Date oar_date_from, Date oar_date_to, Double oar_org_id, Double oar_rol_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(oar_id, oar_date_from, oar_date_to, oar_org_id, oar_rol_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}