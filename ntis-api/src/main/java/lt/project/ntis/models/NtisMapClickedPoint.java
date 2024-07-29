package lt.project.ntis.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import eu.itreegroup.spark.annotations.TypeScriptOptional;

@JsonInclude(Include.NON_NULL)
public class NtisMapClickedPoint {

    private String layer;

    private Integer id;

    private String name;

    @TypeScriptOptional
    private String description;

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}
