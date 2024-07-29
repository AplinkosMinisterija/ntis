package eu.itreegroup.spark.dao.query.objectprocessor;

import java.sql.ResultSet;

import org.springframework.beans.PropertyValue;

public class StatementDoubleGetter extends StatementDataGetter {

    public StatementDoubleGetter(String paramName) {
        super(paramName);
    }

    @Override
    public Object getValueFromResultSet(ResultSet result) throws Exception {
        Double res = result.getDouble(getParamName());
        return result.wasNull() ? null : res;
    }

    @Override
    public PropertyValue getPropertyValue(ResultSet result) throws Exception {
        Double value = (Double) getValueFromResultSet(result);
        return new PropertyValue(getParamName(), value);
    }

    @Override
    public Class<?> getValueClass() {
        return Double.class;
    }
}
