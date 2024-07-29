package eu.itreegroup.spark.modules.tasks.logic.forms.model;

import java.util.List;

import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;

public class SprTaskEditModel {

    // sprTask: created for user name and surname data
    public SprTask sprTask;

    public double progress;

    public SprUsersDAO mainUser;

    public List<SprUsersDAO> assignees;

    List<String> assigneesIds;

    // subTasks: this contains sub tasks the same structure like "sprTask"
    SprTaskEditModel[] subTasks;

    SprFile[] attachments;

    public SprFile[] getAttachments() {
        return attachments;
    }

    public void setAttachments(SprFile[] attachments) {
        this.attachments = attachments;
    }

    public SprTask getSprTask() {
        return sprTask;
    }

    public void setSprTask(SprTask sprTask) {
        this.sprTask = sprTask;
    }

    public SprTaskEditModel[] getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(SprTaskEditModel[] subTasks) {
        this.subTasks = subTasks;
    }

    public List<String> getAssigneesIds() {
        return assigneesIds;
    }

    public void setAssigneesIds(List<String> assigneesIds) {
        this.assigneesIds = assigneesIds;
    }
}
