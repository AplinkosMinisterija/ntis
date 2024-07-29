package eu.itreegroup.spark.modules.admin.logic.forms.model;

import eu.itreegroup.spark.annotations.TypeScriptOptional;
import eu.itreegroup.spark.modules.admin.dao.SprPropertiesDAO;

/**
 * Klasė skirta sistemos parametro duomenų perdavimui
 * 
 */
public class SprPropertiesModel extends SprPropertiesDAO {

    @TypeScriptOptional
    SprFile attachment;

    public SprFile getAttachment() {
        return attachment;
    }

    public void setAttachment(SprFile attachment) {
        this.attachment = attachment;
    }

}
