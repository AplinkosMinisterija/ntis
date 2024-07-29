package lt.project.ntis.logic.forms.model;

import java.util.Date;

import lt.project.ntis.dao.NtisReviewsDAO;

public class NtisReviewCreationModel {

    private NtisReviewsDAO reviewInfo;

    private String orgName;

    private String ordId;

    private String srvType;

    private Date ordDate;

    public NtisReviewsDAO getReviewInfo() {
        return reviewInfo;
    }

    public void setReviewInfo(NtisReviewsDAO reviewInfo) {
        this.reviewInfo = reviewInfo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrdId() {
        return ordId;
    }

    public void setOrdId(String ordId) {
        this.ordId = ordId;
    }

    public String getSrvType() {
        return srvType;
    }

    public void setSrvType(String srvType) {
        this.srvType = srvType;
    }

    public Date getOrdDate() {
        return ordDate;
    }

    public void setOrdDate(Date ordDate) {
        this.ordDate = ordDate;
    }

}
