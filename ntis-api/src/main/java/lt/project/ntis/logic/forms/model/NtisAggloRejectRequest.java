package lt.project.ntis.logic.forms.model;

import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;

public class NtisAggloRejectRequest {

    private Double aggId;

    private String description;

    private SprFile file;

    public Double getAggId() {
        return aggId;
    }

    public void setAggId(Double aggId) {
        this.aggId = aggId;
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

}
