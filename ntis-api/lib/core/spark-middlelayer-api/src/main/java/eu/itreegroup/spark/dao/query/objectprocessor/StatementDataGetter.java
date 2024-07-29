package eu.itreegroup.spark.dao.query.objectprocessor;

import java.sql.ResultSet;

import org.springframework.beans.PropertyValue;

public abstract class StatementDataGetter {

    private String paramName;

    private String paramNameInLowerCase;

    public StatementDataGetter(String paramName) {
        this.setParamName(paramName);
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
        this.paramNameInLowerCase = this.paramName.toLowerCase();
    }

    public String getParamNameInLowerCase() {
        return paramNameInLowerCase;
    }

    public void setParamNameInLowerCase(String paramNameInLowerCase) {
        this.paramNameInLowerCase = paramNameInLowerCase;
    }

    public abstract Object getValueFromResultSet(ResultSet result) throws Exception;

    public abstract PropertyValue getPropertyValue(ResultSet result) throws Exception;

    public abstract Class<?> getValueClass();

    @Override
    public String toString() {
        return "paramName: " + paramName + " for class: " + getValueClass();
    }
}
