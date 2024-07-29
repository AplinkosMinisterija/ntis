package eu.itreegroup.spark.app.model;

import java.util.Arrays;

import eu.itreegroup.spark.modules.admin.logic.forms.model.IdKeyValuePair;

public class AppData {

    private Version version;
    
    private String springProfilesActive;

    private IdKeyValuePair[] properties;

    private IdKeyValuePair[] languages;

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
    
    public String getSpringProfilesActive() {
        return springProfilesActive;
    }

    public void setSpringProfilesActive(String springProfilesActive) {
        this.springProfilesActive = springProfilesActive;
    }

    public IdKeyValuePair[] getProperties() {
        return properties;
    }

    public void setProperties(IdKeyValuePair[] properties) {
        this.properties = properties;
    }

    public IdKeyValuePair[] getLanguages() {
        return languages;
    }

    public void setLanguages(IdKeyValuePair[] languages) {
        this.languages = languages;
    }

    @Override
    public String toString() {
        return "AppData [version=" + version + ", springProfilesActive=" + springProfilesActive + ", properties=" + Arrays.toString(properties) + ", languages="
                + Arrays.toString(languages) + "]";
    }


}
