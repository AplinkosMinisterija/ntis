package eu.itreegroup.spark.dao.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.itreegroup.spark.annotations.TypeScriptOptional;

public class RecordIdentifier {

    private String id;

    @TypeScriptOptional
    private String actionType;

    @JsonIgnore
    public Double getIdAsDouble() {
        return Utils.getDouble(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActionType() {
        return this.actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String toString() {
        return "{id: " + id + " actionType: " + actionType + "}";
    }
}
