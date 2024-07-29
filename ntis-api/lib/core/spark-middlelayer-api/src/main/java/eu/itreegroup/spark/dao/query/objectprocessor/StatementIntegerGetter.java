package eu.itreegroup.spark.dao.query.objectprocessor;

import java.sql.ResultSet;

import org.springframework.beans.PropertyValue;

public class StatementIntegerGetter extends StatementDataGetter {

    public StatementIntegerGetter(String paramName) {
        super(paramName);
    }

    @Override
    public Object getValueFromResultSet(ResultSet result) throws Exception {
        Object obj = result.getObject(getParamName());
        if (obj != null) {
            if (obj instanceof Long) {
                return Integer.valueOf(((Long) obj).toString());
            }
        }
        return obj;
    }

    @Override
    public PropertyValue getPropertyValue(ResultSet result) throws Exception {
        Integer value = (Integer) getValueFromResultSet(result);
        return new PropertyValue(getParamName(), value);
    }

    @Override
    public Class<?> getValueClass() {
        return Integer.class;
    }
}
