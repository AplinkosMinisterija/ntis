package eu.itreegroup.spark.dao.common;

import eu.itreegroup.spark.app.model.FormActions;

public class SprModelBase {

    private FormActions formActions;

    public void setFormActions(FormActions formActions) {
        this.formActions = formActions;
    }

    public FormActions getFormActions() {
        return this.formActions;
    }
}
