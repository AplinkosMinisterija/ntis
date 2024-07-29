package eu.itreegroup.spark.dao.query.objectprocessor;

import java.sql.ResultSet;

import org.springframework.beans.PropertyValue;

import eu.itreegroup.spark.enums.YesNo;

public class StatementBooleanGetter extends StatementDataGetter {

    public StatementBooleanGetter(String paramValue) {
        super(paramValue);
    }

    @Override
    public Object getValueFromResultSet(ResultSet result) throws Exception {
        return result.getString(getParamName());
    }

    @Override
    public PropertyValue getPropertyValue(ResultSet result) throws Exception {
        String value = (String) getValueFromResultSet(result);
        Boolean returnValue = null;
        try {
            YesNo yn = YesNo.getEnumByCode(value);
            returnValue = yn.getBoolean();
        } catch (EnumConstantNotPresentException e) {
            if ("t".equalsIgnoreCase(value)) {
                returnValue = true;
            } else if ("f".equalsIgnoreCase(value)) {
                returnValue = false;
            } else {
                throw new Exception("Could not convert value '" + value + "' to a boolean");
            }
        }
        return new PropertyValue(getParamName(), returnValue);
    }

    @Override
    public Class<?> getValueClass() {
        return String.class;
    }

}
