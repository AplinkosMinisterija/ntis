package eu.itreegroup.spark.dao.query.objectprocessor;

import java.sql.ResultSet;

import org.springframework.beans.PropertyValue;

public class StatementStringGetter extends StatementDataGetter {

    public StatementStringGetter(String paramName) {
        super(paramName);
    }

    @Override
    public Object getValueFromResultSet(ResultSet result) throws Exception {
        return result.getString(getParamName());
    }

    @Override
    public PropertyValue getPropertyValue(ResultSet result) throws Exception {
        String value = (String) getValueFromResultSet(result);
        return new PropertyValue(getParamName(), value);
    }

    @Override
    public Class<?> getValueClass() {
        return String.class;
    }
}
