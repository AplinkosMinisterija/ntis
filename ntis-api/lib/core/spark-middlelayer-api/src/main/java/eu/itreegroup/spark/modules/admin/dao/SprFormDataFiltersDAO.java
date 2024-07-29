package eu.itreegroup.spark.modules.admin.dao;

import java.util.Date;

import eu.itreegroup.spark.modules.admin.dao.gen.SprFormDataFiltersDAOGen;

public class SprFormDataFiltersDAO extends SprFormDataFiltersDAOGen {
   public SprFormDataFiltersDAO() {
      super();
   }
   public SprFormDataFiltersDAO(Double fdf_id, String fdf_code, String fdf_name, String fdf_description, String fdf_content, Double fdf_frm_id, Double fdf_usr_id, Double fdf_rol_id, Date fdf_date_from, Date fdf_date_to, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(fdf_id, fdf_code, fdf_name, fdf_description, fdf_content, fdf_frm_id, fdf_usr_id, fdf_rol_id, fdf_date_from, fdf_date_to, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}