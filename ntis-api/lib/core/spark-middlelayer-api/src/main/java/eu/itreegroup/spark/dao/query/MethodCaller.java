package eu.itreegroup.spark.dao.query;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import eu.itreegroup.spark.dao.query.objectprocessor.StatementDataGetter;

public class MethodCaller {

    protected String methodName;

    protected StatementDataGetter[] statementGetters;

    private Class<?>[] paramClasses;

    public MethodCaller(String methodName, StatementDataGetter[] statementGetters) {
        setMethodName(methodName);
        setStatementGetters(statementGetters);
    }

    public void callObjectMethod(ResultSet resultSet, Object obj) {
        try {
            java.lang.reflect.Method method;
            method = obj.getClass().getMethod(getMethodName(), getParamClasses());
            List<Object> argsValues = new ArrayList<Object>();
            if (getStatementGetters() != null) {
                for (int i = 0; i < getStatementGetters().length; i++) {
                    argsValues.add(getStatementGetters()[i].getValueFromResultSet(resultSet));
                }
            }
            method.invoke(obj, argsValues.toArray());
        } catch (Exception ex) {
            ex.printStackTrace();// FiXME
        }
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public StatementDataGetter[] getStatementGetters() {
        return statementGetters;
    }

    public void setStatementGetters(StatementDataGetter[] statementGetters) {
        this.statementGetters = statementGetters;
        if (this.statementGetters != null) {
            Class<?>[] paramClasses = new Class<?>[this.statementGetters.length];
            for (int i = 0; i < this.statementGetters.length; i++) {
                paramClasses[i] = this.statementGetters[i].getValueClass();
            }
            setParamClasses(paramClasses);
        }
    }

    public Class<?>[] getParamClasses() {
        return paramClasses;
    }

    public void setParamClasses(Class<?>[] paramClasses) {
        this.paramClasses = paramClasses;
    }

}