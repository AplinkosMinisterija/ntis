package lt.project.ntis.dao;

import java.util.Date;

import lt.project.ntis.dao.gen.NtisWastewaterTreatmentOrgDAOGen;

public class NtisWastewaterTreatmentOrgDAO extends NtisWastewaterTreatmentOrgDAOGen {

    public NtisWastewaterTreatmentOrgDAO() {
        super();
    }

    public NtisWastewaterTreatmentOrgDAO(Double wto_id, String wto_name, String wto_address, Double wto_productivity, String wto_domestic_sewage,
            String wto_industrial_sewage, String wto_sludge, String wto_is_it_used, Double wto_ad_id, Double wto_org_id, Double rec_version,
            Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01,
            String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05, String wto_auto_accept,
            String wto_no_notifications) {
        super(wto_id, wto_name, wto_address, wto_productivity, wto_domestic_sewage, wto_industrial_sewage, wto_sludge, wto_is_it_used, wto_ad_id, wto_org_id,
                rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05,
                wto_auto_accept, wto_no_notifications);
    }
}