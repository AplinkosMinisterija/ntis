package eu.itreegroup.spark.modules.admin.logic.forms.model;

import java.util.Date;
import java.util.List;

public class NewsEditModel {

    private Double nwId;

    private String nwTitle;

    private String nwSummary;

    private String nwText;

    private String nwLang;

    private String nwType;

    private Date nwDateFrom;

    private Date nwDateTo;

    private Date nwPublished;

    private List<SprFile> attachment;

    private List<NewsCommentModel> comments;

    public NewsEditModel() {
        super();
    }

    public Double getNwId() {
        return nwId;
    }

    public void setNwId(Double nwId) {
        this.nwId = nwId;
    }

    public String getNwTitle() {
        return nwTitle;
    }

    public void setNwTitle(String nwTitle) {
        this.nwTitle = nwTitle;
    }

    public String getNwSummary() {
        return nwSummary;
    }

    public void setNwSummary(String nwSummary) {
        this.nwSummary = nwSummary;
    }

    public String getNwText() {
        return nwText;
    }

    public void setNwText(String nwText) {
        this.nwText = nwText;
    }

    public String getNwLang() {
        return nwLang;
    }

    public void setNwLang(String nwLang) {
        this.nwLang = nwLang;
    }

    public String getNwType() {
        return nwType;
    }

    public void setNwType(String nwType) {
        this.nwType = nwType;
    }

    public Date getNwDateFrom() {
        return nwDateFrom;
    }

    public void setNwDateFrom(Date nwDateFrom) {
        this.nwDateFrom = nwDateFrom;
    }

    public Date getNwDateTo() {
        return nwDateTo;
    }

    public void setNwDateTo(Date nwDateTo) {
        this.nwDateTo = nwDateTo;
    }

    public Date getNwPublished() {
        return nwPublished;
    }

    public void setNwPublished(Date nwPublished) {
        this.nwPublished = nwPublished;
    }

    public List<SprFile> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<SprFile> attachment) {
        this.attachment = attachment;
    }

    public List<NewsCommentModel> getComments() {
        return comments;
    }

    public void setComments(List<NewsCommentModel> comments) {
        this.comments = comments;
    }

}
