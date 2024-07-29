package eu.itreegroup.spark.dao.common.statementparams;

import java.sql.PreparedStatement;

public class StatementParamInteger implements StatementParam {

    private Integer paramValue;

    public StatementParamInteger(Integer paramValue) {
        setParamValue(paramValue);
    }

    public Integer getParamValue() {
        return paramValue;
    }

    public void setParamValue(Integer paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public void setParameter(PreparedStatement pstmt, int orderInStatement) throws Exception {
        pstmt.setObject(orderInStatement, this.getParamValue());

    }
}
