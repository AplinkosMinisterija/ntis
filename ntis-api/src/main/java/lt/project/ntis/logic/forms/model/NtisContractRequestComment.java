package lt.project.ntis.logic.forms.model;

import eu.itreegroup.spark.annotations.TypeScriptOptional;

public class NtisContractRequestComment {

    @TypeScriptOptional
    private Double cc_id;

    @TypeScriptOptional
    private Double contractId;

    private String author;

    private String time;

    private String text;

    public NtisContractRequestComment() {
        super();
    }

    public NtisContractRequestComment(String author, String time, String text) {
        super();
        this.author = author;
        this.time = time;
        this.text = text;
    }

    public Double getCc_id() {
        return cc_id;
    }

    public void setCc_id(Double cc_id) {
        this.cc_id = cc_id;
    }

    public Double getContractId() {
        return contractId;
    }

    public void setContractId(Double contractId) {
        this.contractId = contractId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
