package eu.itreegroup.spark.modules.admin.rest.model;

import java.util.ArrayList;

import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;

public class PredefinedFilterRestDataModel {

    private String formCode;

    private ArrayList<AdvancedSearchParameterStatement> extendedParams;

    private String predefinedCondition;

    private String filterName;

    private String filterDescription;

    private boolean globalFilter;

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public ArrayList<AdvancedSearchParameterStatement> getExtendedParams() {
        return extendedParams;
    }

    public void setExtendedParams(ArrayList<AdvancedSearchParameterStatement> extendedParams) {
        this.extendedParams = extendedParams;
    }

    public String getPredefinedCondition() {
        return predefinedCondition;
    }

    public void setPredefinedCondition(String predefinedCondition) {
        this.predefinedCondition = predefinedCondition;
    }

    public boolean isGlobalFilter() {
        return globalFilter;
    }

    public void setGlobalFilter(boolean globalFilter) {
        this.globalFilter = globalFilter;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterDescription() {
        return filterDescription;
    }

    public void setFilterDescription(String filterDescription) {
        this.filterDescription = filterDescription;
    }

}
