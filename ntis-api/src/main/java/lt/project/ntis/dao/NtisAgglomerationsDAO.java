package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisAgglomerationsDAOGen;
import java.util.Date;

public class NtisAgglomerationsDAO extends NtisAgglomerationsDAOGen {
   public NtisAgglomerationsDAO() {
      super();
   }
   public NtisAgglomerationsDAO(Double agg_id, Double agg_municipality, String agg_state, Date agg_state_date, String agg_confirmed_document_number, Date agg_confirmed_date, Date agg_created, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05, String agg_agglo_type) {
      super(agg_id, agg_municipality, agg_state, agg_state_date, agg_confirmed_document_number, agg_confirmed_date, agg_created, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05, agg_agglo_type);
   }
}