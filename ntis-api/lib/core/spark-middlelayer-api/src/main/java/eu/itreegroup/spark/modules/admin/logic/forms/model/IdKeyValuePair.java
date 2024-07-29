package eu.itreegroup.spark.modules.admin.logic.forms.model;

import java.io.Serializable;

public class IdKeyValuePair implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2291093915535830593L;

    String id;

    String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "id: " + id + " value: " + value;
    }

}
