package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisAgglomerationVersionsDAOGen;
import java.util.Date;

public class NtisAgglomerationVersionsDAO extends NtisAgglomerationVersionsDAOGen {
   public NtisAgglomerationVersionsDAO() {
      super();
   }
   public NtisAgglomerationVersionsDAO(Double av_id, String av_status, Date av_created, Double av_agg_id, Double av_fil_id, Double av_per_id, String av_admin_review, Date av_admin_review_date, Double av_admin_review_per_id, Double av_admin_review_fil_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(av_id, av_status, av_created, av_agg_id, av_fil_id, av_per_id, av_admin_review, av_admin_review_date, av_admin_review_per_id, av_admin_review_fil_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}