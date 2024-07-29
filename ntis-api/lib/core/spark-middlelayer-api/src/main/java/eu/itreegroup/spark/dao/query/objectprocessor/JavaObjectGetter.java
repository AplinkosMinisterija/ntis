package eu.itreegroup.spark.dao.query.objectprocessor;

import java.sql.ResultSet;

import org.springframework.beans.PropertyValue;

public class JavaObjectGetter extends StatementDataGetter {

    PropertyValue property;

    Class<?> claz;

    public JavaObjectGetter(String paramName, Object obj, Class<?> claz) {
        super(paramName);
        property = new PropertyValue(getParamName(), obj);
        this.claz = claz;

    }

    @Override
    public Object getValueFromResultSet(ResultSet result) throws Exception {
        return property.getValue();
    }

    @Override
    public PropertyValue getPropertyValue(ResultSet result) throws Exception {
        return property;
    }

    @Override
    public Class<?> getValueClass() {
        return claz;
    }

}
