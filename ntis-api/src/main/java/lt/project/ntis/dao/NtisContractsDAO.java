package lt.project.ntis.dao;

import java.util.Date;

import lt.project.ntis.dao.gen.NtisContractsDAOGen;

public class NtisContractsDAO extends NtisContractsDAOGen {

    public NtisContractsDAO() {
        super();
    }

    public NtisContractsDAO(Double cot_id, String cot_code, String cot_created_in_ntis_portal, String cot_state, Date cot_from_date, Date cot_to_date,
            Date cot_created, Date cot_project_created, String cot_rejection_reason, Date cot_rejection_date, String cot_client_phone_no,
            String cot_client_email, Double cot_fil_id, Double cot_wtf_id, Double cot_org_id, Double cot_per_id, Double cot_sign_1_fil_id,
            Double cot_sign_2_fil_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03,
            Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
        super(cot_id, cot_code, cot_created_in_ntis_portal, cot_state, cot_from_date, cot_to_date, cot_created, cot_project_created, cot_rejection_reason,
                cot_rejection_date, cot_client_phone_no, cot_client_email, cot_fil_id, cot_wtf_id, cot_org_id, cot_per_id, cot_sign_1_fil_id, cot_sign_2_fil_id,
                rec_version, rec_create_timestamp, rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
    }

    public String getServicesJson() {
        return this.getC01();
    }

    public void setServicesJson(String services) {
        this.setC01(services);
    }

    public Double getJarOrgId() {
        return this.getN01();
    }

    public void setJarOrgId(Double spOrgId) {
        this.setN01(spOrgId);
    }

    public void setSigningPerId(Double perId) {
        setN02(perId);
    }

    public Double getSigningPerId() {
        return getN02();
    }

    public void setSigningOrgId(Double orgId) {
        setN03(orgId);
    }

    public Double getSigningOrgId() {
        return getN03();
    }
}