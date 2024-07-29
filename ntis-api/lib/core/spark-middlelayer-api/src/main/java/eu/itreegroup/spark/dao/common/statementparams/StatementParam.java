package eu.itreegroup.spark.dao.common.statementparams;

import java.sql.PreparedStatement;

public interface StatementParam {

    public void setParameter(PreparedStatement pstmt, int orderInStatement) throws Exception;
}
