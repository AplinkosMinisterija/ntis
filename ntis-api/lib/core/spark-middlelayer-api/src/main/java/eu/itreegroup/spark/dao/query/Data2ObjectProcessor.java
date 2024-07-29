package eu.itreegroup.spark.dao.query;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

import eu.itreegroup.spark.dao.query.objectprocessor.StatementBooleanGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementDataGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementDateGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementDoubleGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementIntegerGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementStringGetter;

public class Data2ObjectProcessor<S> {

    private Class<S> resultClass;

    private List<MethodCaller> methodCallers;

    public Data2ObjectProcessor(Class<S> resultClass) {
        this.resultClass = resultClass;
    }

    public Data2ObjectProcessor(Class<S> resultClass, ArrayList<MethodCaller> methodCallers) {
        this.resultClass = resultClass;
        setMethodCallers(methodCallers);
    }

    public List<S> getClassData(ResultSet rs) throws Exception {
        List<S> answer = new ArrayList<S>();
        BeanInfo beaninfo = Introspector.getBeanInfo(resultClass);
        PropertyDescriptor[] propertyDescriptor = beaninfo.getPropertyDescriptors();
        ResultSetMetaData rmd = rs.getMetaData();
        ArrayList<String> columnsInStatement = new ArrayList<String>();
        for (int i = 0; i < rmd.getColumnCount(); i++) {
            columnsInStatement.add(rmd.getColumnName(i + 1).toLowerCase());
        }
        Constructor<S> constructor = null;
        constructor = resultClass.getConstructor();
        MutablePropertyValues propValues = new MutablePropertyValues();
        ArrayList<StatementDataGetter> getters = new ArrayList<StatementDataGetter>();
        for (int i = 0; i < propertyDescriptor.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptor[i];
            Class<?> clazz = descriptor.getPropertyType();
            String parameterName = descriptor.getName();
            if (columnsInStatement.contains(parameterName.toLowerCase())) {
                if (clazz.equals(java.lang.String.class)) {
                    getters.add(new StatementStringGetter(descriptor.getName()));
                } else if (clazz.equals(java.util.Date.class)) {
                    getters.add(new StatementDateGetter(descriptor.getName()));
                } else if ("boolean".equals(descriptor.getPropertyType().toString())) {
                    getters.add(new StatementBooleanGetter(descriptor.getName()));
                } else if (clazz.equals(java.lang.Double.class)) {
                    getters.add(new StatementDoubleGetter(descriptor.getName()));
                } else if (clazz.equals(java.lang.Integer.class)) {
                    getters.add(new StatementIntegerGetter(descriptor.getName()));
                } else if (clazz.equals(java.lang.Boolean.class)) {
                    getters.add(new StatementBooleanGetter(descriptor.getName()));
                } else {
                    throw new Exception("Property for name " + descriptor.getName() + " is in not supported type: " + descriptor.getPropertyType());
                }
            }

        }
        while (rs.next()) {
            for (int i = 0; i < getters.size(); i++) {
                if (columnsInStatement.contains(getters.get(i).getParamNameInLowerCase())) {
                    propValues.addPropertyValue(getters.get(i).getPropertyValue(rs));
                }
            }
            S obj = constructor.newInstance();

            PropertyAccessor myAccessor = PropertyAccessorFactory.forBeanPropertyAccess(obj);
            myAccessor.setPropertyValues(propValues);
            if (getMethodCallers() != null) {
                for (int i = 0; i < getMethodCallers().size(); i++) {
                    getMethodCallers().get(i).callObjectMethod(rs, obj);
                }
            }
            answer.add(obj);
        }
        return answer;
    }

    public Class<S> getResultClass() {
        return resultClass;
    }

    public void setResultClass(Class<S> resultClass) {
        this.resultClass = resultClass;
    }

    public List<MethodCaller> getMethodCallers() {
        return methodCallers;
    }

    public void setMethodCallers(List<MethodCaller> methodCallers) {
        this.methodCallers = methodCallers;
    }
}