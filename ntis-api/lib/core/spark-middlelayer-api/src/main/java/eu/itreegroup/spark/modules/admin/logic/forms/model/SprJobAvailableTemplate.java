package eu.itreegroup.spark.modules.admin.logic.forms.model;

public class SprJobAvailableTemplate {

    private Double id;

    private String name;

    public SprJobAvailableTemplate() {
    }

    public SprJobAvailableTemplate(Double id, String name) {
        this.id = id;
        this.name = name;
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
