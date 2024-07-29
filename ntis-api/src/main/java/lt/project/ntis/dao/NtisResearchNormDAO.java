package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisResearchNormDAOGen;
import java.util.Date;

public class NtisResearchNormDAO extends NtisResearchNormDAOGen {
   public NtisResearchNormDAO() {
      super();
   }
   public NtisResearchNormDAO(Double rn_id, String rn_research_type, Double rn_research_norm, String rn_facility_installation_date, Date rn_date_from, Date rn_date_to, Date rn_created, String rn_newest, Double rn_usr_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(rn_id, rn_research_type, rn_research_norm, rn_facility_installation_date, rn_date_from, rn_date_to, rn_created, rn_newest, rn_usr_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}