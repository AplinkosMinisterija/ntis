package eu.itreegroup.spark.dao.query.objectprocessor;

import java.sql.ResultSet;
import java.util.Date;

import org.springframework.beans.PropertyValue;

public class StatementDateGetter extends StatementDataGetter {

    public StatementDateGetter(String paramName) {
        super(paramName);
    }

    @Override
    public Object getValueFromResultSet(ResultSet result) throws Exception {
        return result.getTimestamp(getParamName());
    }

    @Override
    public PropertyValue getPropertyValue(ResultSet result) throws Exception {
        Date value = (Date) getValueFromResultSet(result);
        return new PropertyValue(getParamName(), value);
    }

    @Override
    public Class<?> getValueClass() {
        return Date.class;
    }
}
