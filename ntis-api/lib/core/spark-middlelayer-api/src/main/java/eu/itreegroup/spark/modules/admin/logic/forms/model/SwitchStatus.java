package eu.itreegroup.spark.modules.admin.logic.forms.model;

public class SwitchStatus {

    private Double id;

    private String status;

    public SwitchStatus(Double id, String status) {
        this.id = id;
        this.status = status;
    }

    public SwitchStatus() {
        super();
    }

    public Double getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
