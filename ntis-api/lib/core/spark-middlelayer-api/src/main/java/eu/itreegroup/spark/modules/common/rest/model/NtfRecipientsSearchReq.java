package eu.itreegroup.spark.modules.common.rest.model;

import java.util.List;

import eu.itreegroup.spark.app.model.ForeignKeyParams;

public class NtfRecipientsSearchReq {

    private List<Double> selectedIds;

    private ForeignKeyParams foreignKeyParams;

    public List<Double> getSelectedIds() {
        return selectedIds;
    }

    public void setSelectedIds(List<Double> selectedIds) {
        this.selectedIds = selectedIds;
    }

    public ForeignKeyParams getForeignKeyParams() {
        return foreignKeyParams;
    }

    public void setForeignKeyParams(ForeignKeyParams foreignKeyParams) {
        this.foreignKeyParams = foreignKeyParams;
    }

}
