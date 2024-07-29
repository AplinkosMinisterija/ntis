package eu.itreegroup.spark.modules.admin.dao;

import java.util.Date;

import eu.itreegroup.spark.modules.admin.dao.gen.SprRefDictionariesDAOGen;

public class SprRefDictionariesDAO extends SprRefDictionariesDAOGen {
   public SprRefDictionariesDAO() {
      super();
   }
   public SprRefDictionariesDAO(Double rfd_id, String rfd_subsystem, String rfd_table_name, String rfd_name, String rfd_description, String rfd_code_type, Double rfd_code_length, String rfd_code_colname, String rfd_desc_colname, String rfd_ref_domain_1, String rfd_ref_domain_2, String rfd_ref_domain_3, String rfd_ref_domain_4, String rfd_ref_domain_5, String rfd_n1_colname, String rfd_n2_colname, String rfd_n3_colname, String rfd_n4_colname, String rfd_n5_colname, String rfd_c1_colname, String rfd_c2_colname, String rfd_c3_colname, String rfd_c4_colname, String rfd_c5_colname, String rfd_d1_colname, String rfd_d2_colname, String rfd_d3_colname, String rfd_d4_colname, String rfd_d5_colname, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(rfd_id, rfd_subsystem, rfd_table_name, rfd_name, rfd_description, rfd_code_type, rfd_code_length, rfd_code_colname, rfd_desc_colname, rfd_ref_domain_1, rfd_ref_domain_2, rfd_ref_domain_3, rfd_ref_domain_4, rfd_ref_domain_5, rfd_n1_colname, rfd_n2_colname, rfd_n3_colname, rfd_n4_colname, rfd_n5_colname, rfd_c1_colname, rfd_c2_colname, rfd_c3_colname, rfd_c4_colname, rfd_c5_colname, rfd_d1_colname, rfd_d2_colname, rfd_d3_colname, rfd_d4_colname, rfd_d5_colname, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}