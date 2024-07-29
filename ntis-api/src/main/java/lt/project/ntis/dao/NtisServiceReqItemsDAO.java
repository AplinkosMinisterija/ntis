package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisServiceReqItemsDAOGen;
import java.util.Date;

public class NtisServiceReqItemsDAO extends NtisServiceReqItemsDAOGen {
   public NtisServiceReqItemsDAO() {
      super();
   }
   public NtisServiceReqItemsDAO(Double sri_id, String sri_service_type, String sri_status, Date sri_status_date, Date sri_registration_date, Date sri_removal_date, Double sri_srv_id, Double sri_sr_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      super(sri_id, sri_service_type, sri_status, sri_status_date, sri_registration_date, sri_removal_date, sri_srv_id, sri_sr_id, rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
   }
}