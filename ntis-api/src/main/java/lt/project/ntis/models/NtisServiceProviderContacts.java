package lt.project.ntis.models;

public class NtisServiceProviderContacts {

    private Integer orgId;

    private String email;

    private Boolean emailNotifications;

    private String phoneNumber;

    private String website;

    public NtisServiceProviderContacts() {
        super();
    }

    public NtisServiceProviderContacts(Integer orgId, String email, Boolean emailNotifications, String phoneNumber, String website) {
        super();
        this.orgId = orgId;
        this.email = email;
        this.emailNotifications = emailNotifications;
        this.phoneNumber = phoneNumber;
        this.website = website;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailNotifications() {
        return emailNotifications;
    }

    public void setEmailNotifications(Boolean emailNotifications) {
        this.emailNotifications = emailNotifications;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

}
