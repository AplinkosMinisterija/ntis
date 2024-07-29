package eu.itreegroup.spark.dao.query;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AdvancedSearchParameterStatement {

    private String paramName;

    private AdvancedSearchParameter paramValue;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public AdvancedSearchParameter getParamValue() {
        return paramValue;
    }

    public void setParamValue(AdvancedSearchParameter paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        return "{paramName: " + paramName + " paramValue: " + paramValue + "}";
    }

    @JsonIgnore
    public boolean isEmpty() {
        return paramValue == null || paramValue.isEmpty();
    }
}
