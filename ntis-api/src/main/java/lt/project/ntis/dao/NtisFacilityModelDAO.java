package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisFacilityModelDAOGen;
import java.util.Date;

public class NtisFacilityModelDAO extends NtisFacilityModelDAOGen {
   public NtisFacilityModelDAO() {
      super();
   }
   public NtisFacilityModelDAO(Double fam_id, Double fam_rfc_id, Double fam_pop_equivalent, String fam_tech_pass, Double fam_fil_id, Double fam_chds, Double fam_bds, Double fam_float_material, Double fam_phosphor, Double fam_nitrogen, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05, String fam_manufacturer, String fam_model, String fam_description) {
      super(fam_id, fam_rfc_id, fam_pop_equivalent, fam_tech_pass, fam_fil_id, fam_chds, fam_bds, fam_float_material, fam_phosphor, fam_nitrogen, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05, fam_manufacturer, fam_model, fam_description);
   }
}