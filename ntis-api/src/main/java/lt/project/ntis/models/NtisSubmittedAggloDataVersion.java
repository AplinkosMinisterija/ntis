package lt.project.ntis.models;

import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;

public class NtisSubmittedAggloDataVersion {

    private Double id;

    private String createdDate;

    private String status;

    private String statusCode;

    private SprFile file;

    private String person;

    private String adminReviewPerson;

    private String adminReviewDate;

    private String extent;

    public NtisSubmittedAggloDataVersion() {
        super();
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public SprFile getFile() {
        return file;
    }

    public void setFile(SprFile file) {
        this.file = file;
    }

    public void setFile(String contentType, String key, String name) {
        if (contentType != null && key != null) {
            this.file = new SprFile();
            this.file.setFil_content_type(contentType);
            this.file.setFil_key(key);
            this.file.setFil_name(name);
        } else {
            this.file = null;
        }
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getAdminReviewPerson() {
        return adminReviewPerson;
    }

    public void setAdminReviewPerson(String adminReviewPerson) {
        this.adminReviewPerson = adminReviewPerson;
    }

    public String getAdminReviewDate() {
        return adminReviewDate;
    }

    public void setAdminReviewDate(String adminReviewDate) {
        this.adminReviewDate = adminReviewDate;
    }

    public String getExtent() {
        return extent;
    }

    public void setExtent(String extent) {
        this.extent = extent;
    }

}
