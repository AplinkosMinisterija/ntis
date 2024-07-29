package lt.project.ntis.logic.forms.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Klasė naudojama formų "N4130" ir "G5090" klasės NtisMap funkcijoje getCountiesWithMunicipalities
 */
public class NtisCountyMunicipality {

    private String code;

    private String name;

    private String description;

    private ArrayList<NtisCountyMunicipality> children;

    public NtisCountyMunicipality() {
        super();
    }

    public NtisCountyMunicipality(String code, String name, String description, ArrayList<NtisCountyMunicipality> children) {
        super();
        this.code = code;
        this.name = name;
        this.description = description;
        this.children = children;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public ArrayList<NtisCountyMunicipality> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<NtisCountyMunicipality> children) {
        this.children = children;
    }

    @JsonIgnore
    public void setChildrenJson(String jsonString) {
        if (jsonString != null) {
            this.children = new ArrayList<NtisCountyMunicipality>();
            JSONArray childrenJSON = new JSONArray(jsonString);
            for (int i = 0; i < childrenJSON.length(); i++) {
                JSONObject childJSON = childrenJSON.getJSONObject(i);
                NtisCountyMunicipality child = new NtisCountyMunicipality(childJSON.getString("code"), childJSON.getString("name"),
                        childJSON.getString("description"), null);
                this.children.add(child);
            }
        } else {
            this.children = null;
        }
    }

}
