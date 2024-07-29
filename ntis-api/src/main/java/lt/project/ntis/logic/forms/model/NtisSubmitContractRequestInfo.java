package lt.project.ntis.logic.forms.model;

import java.util.ArrayList;

public class NtisSubmitContractRequestInfo {

    private Double orgId;

    private ArrayList<String> services;

    private Double wtfId;

    private String startDate;

    private String endDate;

    private String applicantEmail;

    private String applicantPhone;

    private ArrayList<String> comments;

    public NtisSubmitContractRequestInfo() {
        super();
    }

    public Double getOrgId() {
        return orgId;
    }

    public void setOrgId(Double orgId) {
        this.orgId = orgId;
    }

    public ArrayList<String> getServices() {
        return services;
    }

    public void setServices(ArrayList<String> services) {
        this.services = services;
    }

    public Double getWtfId() {
        return wtfId;
    }

    public void setWtfId(Double wtfId) {
        this.wtfId = wtfId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public String getApplicantPhone() {
        return applicantPhone;
    }

    public void setApplicantPhone(String applicantPhone) {
        this.applicantPhone = applicantPhone;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

}
