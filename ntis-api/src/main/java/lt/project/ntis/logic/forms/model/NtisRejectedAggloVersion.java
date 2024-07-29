package lt.project.ntis.logic.forms.model;

import java.util.ArrayList;

import org.json.JSONArray;

import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;

public class NtisRejectedAggloVersion {

    private Double avId;

    private String uploadDate;

    private String rejectDate;

    private String statusCode;

    private String status;

    private String person;

    private String description;

    private SprFile file;

    private ArrayList<String> notes;

    public Double getAvId() {
        return avId;
    }

    public void setAvId(Double avId) {
        this.avId = avId;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getRejectDate() {
        return rejectDate;
    }

    public void setRejectDate(String rejectDate) {
        this.rejectDate = rejectDate;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public ArrayList<String> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }

    public void setNotesJson(String jsonString) {
        if (jsonString != null) {
            JSONArray notesJSON = new JSONArray(jsonString);
            ArrayList<String> notes = new ArrayList<String>();
            for (int i = 0; i < notesJSON.length(); i++) {
                notes.add(notesJSON.getString(i));
            }
            this.setNotes(notes);
        } else {
            this.setNotes(null);
        }
    }

}
