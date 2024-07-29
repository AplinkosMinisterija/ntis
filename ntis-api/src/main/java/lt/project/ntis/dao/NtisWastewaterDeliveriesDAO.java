package lt.project.ntis.dao;

import lt.project.ntis.dao.gen.NtisWastewaterDeliveriesDAOGen;
import java.util.Date;

public class NtisWastewaterDeliveriesDAO extends NtisWastewaterDeliveriesDAOGen {
   public NtisWastewaterDeliveriesDAO() {
      super();
   }

public NtisWastewaterDeliveriesDAO(Double wd_id, String wd_sewage_type, Double wd_delivered_quantity, Double wd_used_sludge_quantity,
        String wd_additional_information_sludge_delivery, String wd_delivered_wastewater_description, String wd_state, String wd_rejection_reason,
        String wd_description, Date wd_delivery_date, Double wd_accepted_sewage_quantity, Double wd_cr_id, Double wd_org_id, Double wd_ord_id, Double wd_wto_id,
        Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05,
        String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05, Double wd_wd_id) {
    super(wd_id, wd_sewage_type, wd_delivered_quantity, wd_used_sludge_quantity, wd_additional_information_sludge_delivery, wd_delivered_wastewater_description,
            wd_state, wd_rejection_reason, wd_description, wd_delivery_date, wd_accepted_sewage_quantity, wd_cr_id, wd_org_id, wd_ord_id, wd_wto_id, rec_version,
            rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05, wd_wd_id);
} 
}