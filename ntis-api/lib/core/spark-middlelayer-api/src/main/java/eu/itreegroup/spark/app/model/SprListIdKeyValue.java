package eu.itreegroup.spark.app.model;

import java.io.Serializable;

public class SprListIdKeyValue implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Double id;
    private String key;
    private String display;
    
    public Double getId() {
        return id;
    }
    
    public void setId(Double id) {
        this.id = id;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getDisplay() {
        return display;
    }
    
    public void setDisplay(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return "SprListIdKeyValue [id=" + id + ", key=" + key + ", display=" + display + "]";
    }

}
