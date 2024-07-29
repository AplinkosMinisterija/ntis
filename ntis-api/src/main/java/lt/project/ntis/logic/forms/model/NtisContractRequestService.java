package lt.project.ntis.logic.forms.model;

import eu.itreegroup.spark.annotations.TypeScriptOptional;

public class NtisContractRequestService {

    private String type;

    private String name;

    private String description;

    @TypeScriptOptional
    private Double price;

    @TypeScriptOptional
    private Double cs_id;

    @TypeScriptOptional
    private Double cot_id;

    @TypeScriptOptional
    private Double srv_id;

    public NtisContractRequestService() {
        super();
    }

    public NtisContractRequestService(String type, String name, String description) {
        super();
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCs_id() {
        return cs_id;
    }

    public void setCs_id(Double cs_id) {
        this.cs_id = cs_id;
    }

    public Double getCot_id() {
        return cot_id;
    }

    public void setCot_id(Double cot_id) {
        this.cot_id = cot_id;
    }

    public Double getSrv_id() {
        return srv_id;
    }

    public void setSrv_id(Double srv_id) {
        this.srv_id = srv_id;
    }

}
