package eu.itreegroup.spark.dao.common.statementparams;

import java.sql.PreparedStatement;

public class StatementParamString implements StatementParam {

    private String paramValue;

    public StatementParamString(String paramValue) {
        setParamValue(paramValue);
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public void setParameter(PreparedStatement pstmt, int orderInStatement) throws Exception {
        pstmt.setString(orderInStatement, this.getParamValue());

    }

}
