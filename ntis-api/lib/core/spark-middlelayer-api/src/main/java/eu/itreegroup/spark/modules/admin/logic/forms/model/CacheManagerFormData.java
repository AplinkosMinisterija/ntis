package eu.itreegroup.spark.modules.admin.logic.forms.model;

import java.util.ArrayList;

import eu.itreegroup.spark.app.model.FormActions;

public class CacheManagerFormData {

    private FormActions formActions;

    private ArrayList<CacheInfo> cacheInfo;

    public FormActions getFormActions() {
        return formActions;
    }

    public void setFormActions(FormActions formActions) {
        this.formActions = formActions;
    }

    public ArrayList<CacheInfo> getCacheInfo() {
        return cacheInfo;
    }

    public void setCacheInfo(ArrayList<CacheInfo> cacheInfo) {
        this.cacheInfo = cacheInfo;
    }

}
