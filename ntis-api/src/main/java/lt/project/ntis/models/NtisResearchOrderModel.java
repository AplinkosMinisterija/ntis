package lt.project.ntis.models;

import java.util.ArrayList;
import java.util.Date;

import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import lt.project.ntis.logic.forms.model.NtisWtfInfo;

public class NtisResearchOrderModel {

    private Double ordId;

    private Double ocwId;

    private Date ordCreated;

    private String status;

    private String statusClsf;

    private String orgName;

    private String orgEmail;

    private String orgPhone;

    private Double wtfId;

    private Date requestedDateFrom;

    private Date requestedDateTo;

    private String comment;

    private String rejectionReason;

    private String createdInNtis;

    private Date rejectionDate;

    private String ordererName;

    private String ordererPhone;

    private String ordererEmail;

    private String responsiblePerson;

    private String researchPerson;

    private String samplePerson;

    private Date researchDate;

    private Date sampleDate;

    private Date resultsDate;

    private String resultsPerson;

    private String researchComments;

    private Date completionEstimate;

    private SprFile resultsFile;

    private ArrayList<ResearchRequestedCriteriaModel> selectedCriteria;

    private ArrayList<ResearchCriteriaResultsModel> results;

    private NtisWtfInfo facility;

    private Double rn_id;
    
    private Double revId;
    
    private Double revScore;
    
    private String revComment;

    public Double getOrdId() {
        return ordId;
    }

    public void setOrdId(Double ordId) {
        this.ordId = ordId;
    }

    public Date getOrdCreated() {
        return ordCreated;
    }

    public void setOrdCreated(Date ordCreated) {
        this.ordCreated = ordCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusClsf() {
        return statusClsf;
    }

    public void setStatusClsf(String statusClsf) {
        this.statusClsf = statusClsf;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public NtisWtfInfo getFacility() {
        return facility;
    }

    public void setFacility(NtisWtfInfo facility) {
        this.facility = facility;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Date getRejectionDate() {
        return rejectionDate;
    }

    public void setRejectionDate(Date rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public String getOrdererName() {
        return ordererName;
    }

    public void setOrdererName(String ordererName) {
        this.ordererName = ordererName;
    }

    public String getOrdererPhone() {
        return ordererPhone;
    }

    public void setOrdererPhone(String ordererPhone) {
        this.ordererPhone = ordererPhone;
    }

    public String getOrdererEmail() {
        return ordererEmail;
    }

    public void setOrdererEmail(String ordererEmail) {
        this.ordererEmail = ordererEmail;
    }

    public ArrayList<ResearchRequestedCriteriaModel> getSelectedCriteria() {
        return selectedCriteria;
    }

    public void setSelectedCriteria(ArrayList<ResearchRequestedCriteriaModel> selectedCriteria) {
        this.selectedCriteria = selectedCriteria;
    }

    public Date getCompletionEstimate() {
        return completionEstimate;
    }

    public void setCompletionEstimate(Date completionEstimate) {
        this.completionEstimate = completionEstimate;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getResearchPerson() {
        return researchPerson;
    }

    public void setResearchPerson(String researchPerson) {
        this.researchPerson = researchPerson;
    }

    public String getSamplePerson() {
        return samplePerson;
    }

    public void setSamplePerson(String samplePerson) {
        this.samplePerson = samplePerson;
    }

    public Date getResearchDate() {
        return researchDate;
    }

    public void setResearchDate(Date researchDate) {
        this.researchDate = researchDate;
    }

    public Date getSampleDate() {
        return sampleDate;
    }

    public void setSampleDate(Date sampleDate) {
        this.sampleDate = sampleDate;
    }

    public Date getRequestedDateFrom() {
        return requestedDateFrom;
    }

    public void setRequestedDateFrom(Date requestedDateFrom) {
        this.requestedDateFrom = requestedDateFrom;
    }

    public Date getRequestedDateTo() {
        return requestedDateTo;
    }

    public void setRequestedDateTo(Date requestedDateTo) {
        this.requestedDateTo = requestedDateTo;
    }

    public Double getOcwId() {
        return ocwId;
    }

    public void setOcwId(Double ocwId) {
        this.ocwId = ocwId;
    }

    public ArrayList<ResearchCriteriaResultsModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResearchCriteriaResultsModel> results) {
        this.results = results;
    }

    public String getResearchComments() {
        return researchComments;
    }

    public void setResearchComments(String researchComments) {
        this.researchComments = researchComments;
    }

    public SprFile getResultsFile() {
        return resultsFile;
    }

    public void setResultsFile(SprFile resultsFile) {
        this.resultsFile = resultsFile;
    }

    public Date getResultsDate() {
        return resultsDate;
    }

    public void setResultsDate(Date resultsDate) {
        this.resultsDate = resultsDate;
    }

    public String getResultsPerson() {
        return resultsPerson;
    }

    public void setResultsPerson(String resultsPerson) {
        this.resultsPerson = resultsPerson;
    }

    public Double getWtfId() {
        return wtfId;
    }

    public void setWtfId(Double wtfId) {
        this.wtfId = wtfId;
    }

    public String getCreatedInNtis() {
        return createdInNtis;
    }

    public void setCreatedInNtis(String createdInNtis) {
        this.createdInNtis = createdInNtis;
    }

    public Double getRn_id() {
        return rn_id;
    }

    public void setRn_id(Double rn_id) {
        this.rn_id = rn_id;
    }

    public Double getRevId() {
        return revId;
    }

    public void setRevId(Double revId) {
        this.revId = revId;
    }

    public Double getRevScore() {
        return revScore;
    }

    public void setRevScore(Double revScore) {
        this.revScore = revScore;
    }

    public String getRevComment() {
        return revComment;
    }

    public void setRevComment(String revComment) {
        this.revComment = revComment;
    }

}
