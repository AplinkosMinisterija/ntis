package eu.itreegroup.spark.modules.admin.logic.forms.model;

import java.util.List;

import eu.itreegroup.spark.modules.admin.dao.SprTemplateTextsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprTemplatesDAO;

public class SprTemplate extends SprTemplatesDAO {

    private List<SprTemplateTextsDAO> texts;

    public List<SprTemplateTextsDAO> getTexts() {
        return texts;
    }

    public void setTexts(List<SprTemplateTextsDAO> texts) {
        this.texts = texts;
    }

}
