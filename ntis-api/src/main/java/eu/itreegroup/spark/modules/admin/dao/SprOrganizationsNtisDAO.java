package eu.itreegroup.spark.modules.admin.dao;

import java.util.Date;

import eu.itreegroup.spark.constants.DbConstants;

public class SprOrganizationsNtisDAO extends SprOrganizationsDAO {

    public SprOrganizationsNtisDAO() {
        super();
    }

    public SprOrganizationsNtisDAO(Double org_id, String org_name, String org_code, String org_type, String org_region, String org_phone, String org_email,
            String org_website, String org_address, String org_house_number, String org_bank_account, String org_bank_name, String org_delegation_person,
            String org_delegation_person_position, Date org_date_from, Date org_date_to, Double org_org_id, Double rec_version, Date rec_create_timestamp,
            String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04,
            String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
        super(org_id, org_name, org_code, org_type, org_region, org_phone, org_email, org_website, org_address, org_house_number, org_bank_account,
                org_bank_name, org_delegation_person, org_delegation_person_position, org_date_from, org_date_to, org_org_id, rec_version, rec_create_timestamp,
                rec_userid, rec_timestamp, n01, n02, n03, n04, n05, c01, c02, c03, c04, c05, d01, d02, d03, d04, d05);
    }

    public String getOrgType() {
        return this.getC01();
    }

    public void setOrgType(String orgType) {
        this.setC01(orgType);
    }

    public Boolean getEmailNotifications() {
        return DbConstants.BOOLEAN_TRUE.equals(this.getC02());
    }

    public void setEmailNotifications(Boolean emailNotifications) {
        this.setC02(emailNotifications == null || emailNotifications ? DbConstants.BOOLEAN_TRUE : DbConstants.BOOLEAN_FALSE);
    }

    public String getWebsite() {
        return this.getC03();
    }

    public void setWebsite(String website) {
        this.setC03(website);
    }

    public String getOrgRejectionReason() {
        return this.getC04();
    }

    public void setOrgRejectionReason(String orgRejectionReason) {
        this.setC04(orgRejectionReason);
    }

    public String getLoginDefaultRole() {
        return this.getC05();
    }

    public void setLoginDefaultRole(String loginDefaultRole) {
        this.setC05(loginDefaultRole);
    }

    public Double getOrgState() {
        return this.getN01();
    }

    public void setOrgState(Double orgState) {
        this.setN01(orgState);
    }

    public Double getMunicipalityCode() {
        return this.getN02();
    }

    public void setMunicipalityCode(Double municipalityCode) {
        this.setN02(municipalityCode);
    }

    public Double getRcOrgType() {
        return this.getN03();
    }

    public void setRcOrgType(Double rcOrgType) {
        this.setN03(rcOrgType);
    }

    public void setManageOrgRoles(Double manageOrgRoles) {
        this.setN04(manageOrgRoles);
    }

    public Double getManagerOrgRoles() {
        return this.getN04();
    }

    public void setAdminUsrId(Double adminUsrId) {
        this.setN05(adminUsrId);
    }

    public Double getAdminUsrId() {
        return this.getN05();
    }

    public Date getOrgRegisteredDate() {
        return this.getD01();
    }

    public void setOrgRegisteredDate(Date orgRegisteredDate) {
        this.setD01(orgRegisteredDate);
    }

    public Date getOrgDeregisteredDate() {
        return this.getD02();
    }

    public void setOrgDeregisteredDate(Date orgDeregisteredDate) {
        this.setD02(orgDeregisteredDate);
    }

    public Date getFacilityInstallerDateFrom() {
        return this.getD03();
    }

    public void setFacilityInstallerDateFrom(Date facilityInstallerDateFrom) {
        this.setD03(facilityInstallerDateFrom);
    }

    public void setRenewFromRcDate(Date renewFromRcDate) {
        this.setD04(renewFromRcDate);
    }

    public Date getRenewFromRcDate() {
        return this.getD04();
    }
}