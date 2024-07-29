package eu.itreegroup.spark.modules.admin.logic.forms.model;

import java.util.Date;
import java.util.List;

public class NewsCommentModel {

    private Double nwcId;

    private String nwcComment;

    private Date nwcCreateDate;

    private Double nwcUsrId;

    private Double nwcNwId;

    private String nwcUser;

    private Double nwcNwcId;

    private Boolean canDeleteComment;

    private Boolean canEditComment;

    private List<NewsCommentModel> replyComments;

    public NewsCommentModel() {
        super();
    }

    public Double getNwcId() {
        return nwcId;
    }

    public void setNwcId(Double nwcId) {
        this.nwcId = nwcId;
    }

    public String getNwcComment() {
        return nwcComment;
    }

    public void setNwcComment(String nwcComment) {
        this.nwcComment = nwcComment;
    }

    public Date getNwcCreateDate() {
        return nwcCreateDate;
    }

    public void setNwcCreateDate(Date nwcCreateDate) {
        this.nwcCreateDate = nwcCreateDate;
    }

    public Double getNwcUsrId() {
        return nwcUsrId;
    }

    public void setNwcUsrId(Double nwcUsrId) {
        this.nwcUsrId = nwcUsrId;
    }

    public Double getNwcNwId() {
        return nwcNwId;
    }

    public void setNwcNwId(Double nwcNwId) {
        this.nwcNwId = nwcNwId;
    }

    public String getNwcUser() {
        return nwcUser;
    }

    public void setNwcUser(String nwcUser) {
        this.nwcUser = nwcUser;
    }

    public Double getNwcNwcId() {
        return nwcNwcId;
    }

    public void setNwcNwcId(Double nwcNwcId) {
        this.nwcNwcId = nwcNwcId;
    }

    public Boolean getCanEditComment() {
        return canEditComment;
    }

    public void setCanEditComment(Boolean canEditComment) {
        this.canEditComment = canEditComment;
    }

    public List<NewsCommentModel> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<NewsCommentModel> replyComments) {
        this.replyComments = replyComments;
    }

    public Boolean getCanDeleteComment() {
        return canDeleteComment;
    }

    public void setCanDeleteComment(Boolean canDeleteComment) {
        this.canDeleteComment = canDeleteComment;
    }

}
