package lt.project.ntis.logic.forms.model;

import eu.itreegroup.spark.annotations.TypeScriptOptional;
import eu.itreegroup.spark.modules.admin.logic.forms.model.NewsEditModel;

/**
 * Klasė skirta NTIS naujienų informacijos modeliui apibrėžti
 */
public class NtisNewsEditModel extends NewsEditModel {

    private String isPublic;

    private String isTemplate;

    private String pageTemplateType;

    @TypeScriptOptional
    private boolean saveAsNewTemplate;

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getIsTemplate() {
        return isTemplate;
    }

    public void setIsTemplate(String isTemplate) {
        this.isTemplate = isTemplate;
    }

    public boolean isSaveAsNewTemplate() {
        return saveAsNewTemplate;
    }

    public void setSaveAsNewTemplate(boolean saveAsNewTemplate) {
        this.saveAsNewTemplate = saveAsNewTemplate;
    }

    public String getPageTemplateType() {
        return pageTemplateType;
    }

    public void setPageTemplateType(String pageTemplateType) {
        this.pageTemplateType = pageTemplateType;
    }

}
