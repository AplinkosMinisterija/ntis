package eu.itreegroup.spark.dao.common.statementparams;

import java.sql.PreparedStatement;
import java.util.Date;

import eu.itreegroup.spark.dao.common.Utils;

public class StatementParamDate implements StatementParam {

    private Date paramValue;

    public StatementParamDate(Date paramValue) {
        setParamValue(paramValue);
    }

    public Date getParamValue() {
        return paramValue;
    }

    public void setParamValue(Date paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public void setParameter(PreparedStatement pstmt, int orderInStatement) throws Exception {
        pstmt.setDate(orderInStatement, Utils.getSqlDate(this.getParamValue()));
    }

}
