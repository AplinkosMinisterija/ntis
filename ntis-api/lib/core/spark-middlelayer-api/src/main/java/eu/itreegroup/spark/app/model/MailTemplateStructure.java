package eu.itreegroup.spark.app.model;

public class MailTemplateStructure {

    private String subject;

    private String body;

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

    public String toString() {
        return "subject: " + subject + " body: " + body;
    }
}
