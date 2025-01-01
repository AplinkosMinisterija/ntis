package lt.project.ntis.logic.forms.model;

import java.util.Date;

public class NtisSystemWorksEditModel {

    public Double nswId;

    public Date startDate;

    public Date endDate;

    public Date worksDateFrom;

    public Date worksDateTo;

    public String additionalInformation;

    public String isActive;

    public Double getNswId() {
        return nswId;
    }

    public void setNswId(Double nswId) {
        this.nswId = nswId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getWorksDateFrom() {
        return worksDateFrom;
    }

    public void setWorksDateFrom(Date worksDateFrom) {
        this.worksDateFrom = worksDateFrom;
    }

    public Date getWorksDateTo() {
        return worksDateTo;
    }

    public void setWorksDateTo(Date worksDateTo) {
        this.worksDateTo = worksDateTo;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "NtisSystemWorksEditModel [nswId=" + nswId + ", startDate=" + startDate + ", endDate=" + endDate + ", worksDateFrom=" + worksDateFrom
                + ", worksDateTo=" + worksDateTo + ", additionalInformation=" + additionalInformation + ", isActive=" + isActive + "]";
    }

}
