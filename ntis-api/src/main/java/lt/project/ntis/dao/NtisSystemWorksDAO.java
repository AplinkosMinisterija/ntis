package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisSystemWorksDAOGen;
import java.util.Date;

public class NtisSystemWorksDAO extends NtisSystemWorksDAOGen {
   public NtisSystemWorksDAO() {
      super();
   }
   public NtisSystemWorksDAO(Double nsw_id, Date nsw_show_date_from, Date nsw_show_date_to, Date nsw_works_date_from, Date nsw_works_date_to, String nsw_additional_information, String nsw_is_active, String nsw_notification_sent, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(nsw_id, nsw_show_date_from, nsw_show_date_to, nsw_works_date_from, nsw_works_date_to, nsw_additional_information, nsw_is_active, nsw_notification_sent, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}