package lt.project.ntis.logic.forms.model;

import java.util.Date;

public class NtisSystemWorksInfo {

    public Date startDate;

    public Date endDate;

    public String worksDateFrom;

    public String worksDateTo;

    public String additionalInformation;

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

    public String getWorksDateFrom() {
        return worksDateFrom;
    }

    public void setWorksDateFrom(String worksDateFrom) {
        this.worksDateFrom = worksDateFrom;
    }

    public String getWorksDateTo() {
        return worksDateTo;
    }

    public void setWorksDateTo(String worksDateTo) {
        this.worksDateTo = worksDateTo;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    @Override
    public String toString() {
        return "NtisSystemWorksInfo [startDate=" + startDate + ", endDate=" + endDate + ", worksDateFrom=" + worksDateFrom + ", worksDateTo=" + worksDateTo
                + ", additionalInformation=" + additionalInformation + "]";
    }

}
