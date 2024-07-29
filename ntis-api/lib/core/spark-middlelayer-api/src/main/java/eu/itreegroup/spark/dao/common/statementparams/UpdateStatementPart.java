package eu.itreegroup.spark.dao.common.statementparams;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpdateStatementPart {

    private String statementPart;

    private ArrayList<StatementParam> statementParams;

    public UpdateStatementPart() {
        this.statementParams = new ArrayList<StatementParam>();
    }

    public String getStatementPart() {
        return statementPart;
    }

    public void setStatementPart(String statementPart) {
        this.statementPart = statementPart;
    }

    public List<StatementParam> getStatementParams() {
        return statementParams;
    }

    public void setStatementParams(ArrayList<StatementParam> statementParams) {
        this.statementParams = statementParams;
    }

    public void addStatementParam(String paramValue) {
        statementParams.add(new StatementParamString(paramValue));
    }

    public void addStatementParam(Double paramValue) {
        statementParams.add(new StatementParamDouble(paramValue));
    }

    public void addStatementParam(Date paramValue) {
        statementParams.add(new StatementParamDate(paramValue));
    }

    public void addStatementTimestampParam(Date paramValue) {
        statementParams.add(new StatementParamTimestamp(paramValue));
    }

    public void addStatementParam(Timestamp paramValue) {
        statementParams.add(new StatementParamTimestamp(paramValue));
    }

    public void addStatementParam(Integer paramValue) {
        statementParams.add(new StatementParamInteger(paramValue));
    }
}
