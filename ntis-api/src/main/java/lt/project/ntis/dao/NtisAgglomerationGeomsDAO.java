package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisAgglomerationGeomsDAOGen;
import java.util.Date;

public class NtisAgglomerationGeomsDAO extends NtisAgglomerationGeomsDAOGen {
   public NtisAgglomerationGeomsDAO() {
      super();
   }
   public NtisAgglomerationGeomsDAO(Double ag_id, String ag_uuid, Double ag_municipality, String ag_name, Double ag_population, Double ag_density, Double ag_area, Date ag_obj_date, Double ag_agg_id, Double ag_av_id, String ag_status, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(ag_id, ag_uuid, ag_municipality, ag_name, ag_population, ag_density, ag_area, ag_obj_date, ag_agg_id, ag_av_id, ag_status, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}