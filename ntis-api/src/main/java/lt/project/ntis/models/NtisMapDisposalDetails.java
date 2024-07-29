package lt.project.ntis.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import eu.itreegroup.spark.annotations.TypeScriptOptional;

@JsonInclude(Include.NON_NULL)
public class NtisMapDisposalDetails {

    @TypeScriptOptional
    private Double id;

    @TypeScriptOptional
    private Double wtfId;

    @TypeScriptOptional
    private String address;

    @TypeScriptOptional
    private String disposalDate;

    @TypeScriptOptional
    private String stateName;

    @TypeScriptOptional
    private String link;

    @TypeScriptOptional
    private Double x;

    @TypeScriptOptional
    private Double y;

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public Double getWtfId() {
        return wtfId;
    }

    public void setWtfId(Double wtfId) {
        this.wtfId = wtfId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(String disposalDate) {
        this.disposalDate = disposalDate;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

}
