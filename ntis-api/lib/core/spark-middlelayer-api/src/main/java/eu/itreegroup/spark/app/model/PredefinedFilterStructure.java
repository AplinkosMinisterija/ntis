package eu.itreegroup.spark.app.model;

import java.util.ArrayList;

import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;

public class PredefinedFilterStructure {

    private ArrayList<AdvancedSearchParameterStatement> extendedParams;

    private String predefinedCondition;

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

    @Override
    public String toString() {
        return "extendedParams: " + extendedParams + " predefinedCondition: " + predefinedCondition;
    }
}
