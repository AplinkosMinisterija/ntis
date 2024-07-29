package lt.project.ntis.logic.forms.model;

import java.util.Date;

/**
 * Klasė skirta pranešimo informacijos modeliui apibrėžti
 *
 */
public class NtisNotificationViewModel {

    private String subject;

    private String body;

    private Date date;

    private Double refId;

    private String refType;

    private String msgSubject;

    private NtisNotificationContactsModel contactInfo;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getRefId() {
        return refId;
    }

    public void setRefId(Double refId) {
        this.refId = refId;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getMsgSubject() {
        return msgSubject;
    }

    public void setMsgSubject(String msgSubject) {
        this.msgSubject = msgSubject;
    }

    public NtisNotificationContactsModel getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(NtisNotificationContactsModel contactInfo) {
        this.contactInfo = contactInfo;
    }

}
