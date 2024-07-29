package eu.itreegroup.spark.modules.common.rest.model;

import java.util.ArrayList;

public class NotificationRequest {

    private Double ntfReference;

    private String ntfTitle;

    private String ntfMessage;

    private ArrayList<NotificationRecipient> recipients;

    public Double getNtfReference() {
        return ntfReference;
    }

    public void setNtfReference(Double ntfReference) {
        this.ntfReference = ntfReference;
    }

    public String getNtfTitle() {
        return ntfTitle;
    }

    public void setNtfTitle(String ntfTitle) {
        this.ntfTitle = ntfTitle;
    }

    public String getNtfMessage() {
        return ntfMessage;
    }

    public void setNtfMessage(String ntfMessage) {
        this.ntfMessage = ntfMessage;
    }

    public ArrayList<NotificationRecipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(ArrayList<NotificationRecipient> recipients) {
        this.recipients = recipients;
    }

}
