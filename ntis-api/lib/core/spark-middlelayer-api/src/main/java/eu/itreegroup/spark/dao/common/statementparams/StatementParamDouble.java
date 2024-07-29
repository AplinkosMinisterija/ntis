package eu.itreegroup.spark.dao.common.statementparams;

import java.sql.PreparedStatement;

public class StatementParamDouble implements StatementParam {

    private Double paramValue;

    public StatementParamDouble(Double paramValue) {
        setParamValue(paramValue);
    }

    public Double getParamValue() {
        return paramValue;
    }

    public void setParamValue(Double paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public void setParameter(PreparedStatement pstmt, int orderInStatement) throws Exception {
        pstmt.setObject(orderInStatement, this.getParamValue());
    }

}
