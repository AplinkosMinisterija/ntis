package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisReviewsDAOGen;
import java.util.Date;

public class NtisReviewsDAO extends NtisReviewsDAOGen {
   public NtisReviewsDAO() {
      super();
   }
   public NtisReviewsDAO(Double rev_id, String rev_comment, Double rev_score, Double rev_usr_id, Double rev_ord_id, Double rev_wd_id, Double rev_pasl_org_id, Double rev_vand_org_id, Date rev_completed_date, String rev_receiver_read, String rev_admin_read, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(rev_id, rev_comment, rev_score, rev_usr_id, rev_ord_id, rev_wd_id, rev_pasl_org_id, rev_vand_org_id, rev_completed_date, rev_receiver_read, rev_admin_read, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}