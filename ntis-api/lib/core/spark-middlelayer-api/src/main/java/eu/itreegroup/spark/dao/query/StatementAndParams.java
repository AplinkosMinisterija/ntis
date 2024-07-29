package eu.itreegroup.spark.dao.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementSetter;

import eu.itreegroup.spark.dao.common.Utils;

public class StatementAndParams implements PreparedStatementSetter {

    public static String AND_OPERAND = " AND ";

    public static String OR_OPERAND = " OR ";

    public static final int PARAM_DOUBLE = 1;

    public static final int PARAM_STRING = 2;

    public static final int PARAM_DATE = 3;

    public static final int PARAM_DATE_TIME = 4;

    public static final String PARAM_IS_NULL_CONDITNION = "null";

    public static final String PARAM_IS_NOT_NULL_CONDITNION = "notnull";

    public static final String PARAM_IS_NOT_EQUAL = "!=";

    public static final String PARAM_IS_BETWEEN = "[]";

    private String statement = null;

    private String statementGroupPart = null;

    private String statementOrderPart = null;

    private String predefinedConditionStetement = null;

    private String recordCountLimitPart = null;

    private boolean whereExists;

    private ArrayList<SelectParamValue> selectParams;

    private ArrayList<SelectParam4WherePart> selectParams4WherePart;

    private ArrayList<String> jsonColumns;

    public StatementAndParams() {
        this.selectParams = new ArrayList<SelectParamValue>();
        this.selectParams4WherePart = new ArrayList<SelectParam4WherePart>();
        jsonColumns = new ArrayList<String>();
        whereExists = false;
    }

    public StatementAndParams(String statement) {
        this.setStatement(statement);
        this.selectParams = new ArrayList<SelectParamValue>();
        this.selectParams4WherePart = new ArrayList<SelectParam4WherePart>();
        jsonColumns = new ArrayList<String>();
        whereExists = false;
    }

    private String addIfNotExists(String str, String prefix) {
        if (!str.toLowerCase().trim().startsWith(prefix.toLowerCase().trim())) {
            return prefix + " " + str;
        } else {
            return str;
        }
    }

    public String getStatemenWithParams() {
        StringBuffer answer = new StringBuffer(this.getStatement());
        boolean whereExistsForselect = isWhereExists();
        if (getPredefinedConditionStetement() != null && !getPredefinedConditionStetement().isBlank()) {
            if (!whereExistsForselect) {
                answer.append(" WHERE ");
                whereExistsForselect = true;
            } else {
                answer.append(AND_OPERAND);
            }
            answer.append(getPredefinedConditionStetement());
        }
        for (int i = 0; i < selectParams4WherePart.size(); i++) {
            SelectParam4WherePart sp = selectParams4WherePart.get(i);
            if (sp.isUsable()) {
                String condition = sp.getSelectStmtPart();
                if (!whereExistsForselect) {
                    answer.append(" WHERE ");
                    whereExistsForselect = true;
                } else {
                    answer.append(sp.getOperand());
                }
                answer.append(condition);
            }
        }
        if (getStatementGroupPart() != null) {
            answer.append("\r\n");
            answer.append(addIfNotExists(getStatementGroupPart(), " GROUP BY "));
        }
        if (getStatementOrderPart() != null) {
            answer.append("\r\n");
            answer.append(addIfNotExists(getStatementOrderPart(), " ORDER BY "));
        }
        if (getRecordCountLimitPart() != null) {
            answer.append("\r\n");
            answer.append(getRecordCountLimitPart());
        }
        return answer.toString();
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public void addParam4WherePart(String stmt, String operand, String paramValue) {
        SelectParam4WherePart sp = new SelectParam4WherePart();
        sp.setSelectStmtPart(stmt);
        sp.setOperand(operand);
        sp.addParam(paramValue);
        this.selectParams4WherePart.add(sp);
    }

    public void addParam4WherePart(String stmt, String operand, Date paramValue) {
        SelectParam4WherePart sp = new SelectParam4WherePart();
        sp.setSelectStmtPart(stmt);
        sp.setOperand(operand);
        sp.addParam(paramValue);
        this.selectParams4WherePart.add(sp);
    }

    public void addParam4WherePart(String stmt, String operand, Double paramValue) {
        SelectParam4WherePart sp = new SelectParam4WherePart();
        sp.setSelectStmtPart(stmt);
        sp.addParam(paramValue);
        sp.setOperand(operand);
        this.selectParams4WherePart.add(sp);
    }

    public void addParam4WherePart(String stmt, String operand, Integer paramValue) {
        SelectParam4WherePart sp = new SelectParam4WherePart();
        sp.setSelectStmtPart(stmt);
        sp.addParam(paramValue);
        sp.setOperand(operand);
        this.selectParams4WherePart.add(sp);
    }

    public void addParam4WherePart(String stmt, String operand, List<SelectParamValue> paramValues) {
        SelectParam4WherePart sp = new SelectParam4WherePart();
        sp.setSelectStmtPart(stmt);
        sp.setParams(paramValues);
        sp.setOperand(operand);
        this.selectParams4WherePart.add(sp);
    }

    public void addParam4WherePart(String stmt, String operand, SelectParamValue... paramValues) {
        SelectParam4WherePart sp = new SelectParam4WherePart();
        sp.setSelectStmtPart(stmt);
        for (SelectParamValue paramValue : paramValues) {
            sp.addParam(paramValue);
        }
        sp.setOperand(operand);
        this.selectParams4WherePart.add(sp);
    }

    public void addCondition4WherePart(String stmt, String operand) {
        SelectParam4WherePart sp = new SelectParam4WherePart(true);
        sp.setSelectStmtPart(stmt);
        sp.setOperand(operand);
        this.selectParams4WherePart.add(sp);

    }

    public void addParam4WherePart(String stmt, String operand, int paramType, AdvancedSearchParameterStatement paramValues) throws Exception {
        addParam4WherePart(stmt, operand, paramType, paramValues, null);
    }

    public void addParam4WherePart(String stmt, String operand, int paramType, AdvancedSearchParameterStatement paramValues, String format) throws Exception {
        if (paramValues != null) {
            String condition = paramValues.getParamValue().getCondition();
            String value = paramValues.getParamValue().getValue();
            String upperLower = paramValues.getParamValue().getUpperLower();
            if (PARAM_IS_NULL_CONDITNION.equals(condition)) {
                addCondition4WherePart(stmt + " is null ", operand);
            } else {
                if (PARAM_IS_NOT_NULL_CONDITNION.equals(condition)) {
                    addCondition4WherePart(stmt + " is not null ", operand);
                } else {
                    if (paramValues.getParamValue().getValues() != null && paramValues.getParamValue().getValues().size() > 0) {
                        if (paramType == PARAM_DATE && PARAM_IS_BETWEEN.equals(condition)) {
                            stmt = stmt + " between ? and ? ";
                        } else {
                            stmt = stmt + " in (";
                            for (int i = 0; i < paramValues.getParamValue().getValues().size(); i++) {
                                if (i != 0) {
                                    stmt = stmt + ", ";
                                }
                                stmt = stmt + "?";
                            }
                            stmt = stmt + ") ";
                        }
                    } else {
                        if (paramType == PARAM_STRING) {
                            boolean likeExists = condition.indexOf('%') != -1;
                            if (AdvancedSearchParameter.REGURAL.equals(upperLower)) {
                                stmt = stmt + " " + (likeExists ? "like" : condition) + " ?";
                            } else {
                                if (AdvancedSearchParameter.LOWERCASE.equals(upperLower) || AdvancedSearchParameter.UPPERCASE.equals(upperLower)) {
                                    stmt = " lower(" + stmt + ") " + (likeExists ? "like" : condition) + " lower(?)";
                                } else {
                                    if (AdvancedSearchParameter.REGULAR_LATIN.equals(upperLower)) {
                                        stmt = " " + Utils.getSqlForTranslation(stmt) + " " + (likeExists ? "like" : condition) + " "
                                                + Utils.getSqlForTranslation("?");
                                    } else {
                                        if (AdvancedSearchParameter.CASE_INSENSITIVE_LATIN.equals(upperLower)) {
                                            stmt = " lower(" + Utils.getSqlForTranslation(stmt) + ") " + (likeExists ? "like" : condition) + " lower("
                                                    + Utils.getSqlForTranslation("?") + ")";
                                        }
                                    }
                                }
                            }
                            if (likeExists && value != null && !value.isEmpty()) {
                                value = condition.replace("-", value);
                            }
                        } else {
                            if (PARAM_IS_NOT_EQUAL.equals(condition)) {
                                stmt = " (" + stmt + " " + condition + " ? or " + stmt + " is null) ";
                            } else {
                                stmt = stmt + " " + condition + " ? ";
                            }
                        }
                    }
                    switch (paramType) {
                        case PARAM_DOUBLE: {
                            if (paramValues.getParamValue().getValues() != null && paramValues.getParamValue().getValues().size() > 0) {
                                List<SelectParamValue> prmValues = new ArrayList<SelectParamValue>();
                                List<String> prmValuesData = paramValues.getParamValue().getValues();
                                for (int i = 0; i < prmValuesData.size(); i++) {
                                    SelectParamValue selParamValue = new SelectParamValue();
                                    selParamValue.setNumberValue(Utils.getDouble(prmValuesData.get(i)));
                                    prmValues.add(selParamValue);
                                }
                                addParam4WherePart(stmt, operand, prmValues);
                            } else {
                                addParam4WherePart(stmt, operand, Utils.getDouble(value));
                            }
                            break;
                        }
                        case PARAM_STRING: {
                            if (paramValues.getParamValue().getValues() != null && paramValues.getParamValue().getValues().size() > 0) {
                                List<SelectParamValue> prmValues = new ArrayList<SelectParamValue>();
                                List<String> prmValuesData = paramValues.getParamValue().getValues();
                                for (int i = 0; i < prmValuesData.size(); i++) {
                                    prmValues.add(new SelectParamValue(prmValuesData.get(i)));
                                }
                                addParam4WherePart(stmt, operand, prmValues);
                            } else {
                                addParam4WherePart(stmt, operand, value);
                            }
                            break;
                        }
                        case PARAM_DATE: {
                            if (PARAM_IS_BETWEEN.equals(condition)) {
                                List<SelectParamValue> prmValues = new ArrayList<SelectParamValue>();
                                List<String> prmValuesData = paramValues.getParamValue().getValues();
                                for (int i = 0; i < prmValuesData.size(); i++) {
                                    Date date = null;
                                    value = prmValuesData.get(i);
                                    if (value != null && !"".equals(value)) {
                                        date = Utils.getDateFromString(value, format);
                                    }
                                    prmValues.add(new SelectParamValue(date));
                                }
                                addParam4WherePart(stmt, operand, prmValues);
                            } else {
                                Date date = null;
                                if (value != null && !"".equals(value)) {
                                    date = Utils.getDateFromString(value, format);
                                }
                                addParam4WherePart(stmt, operand, date);
                            }
                            break;
                        }
                        case PARAM_DATE_TIME: {
                            if (PARAM_IS_BETWEEN.equals(condition)) {
                                List<SelectParamValue> prmValues = new ArrayList<SelectParamValue>();
                                List<String> prmValuesData = paramValues.getParamValue().getValues();
                                for (int i = 0; i < prmValuesData.size(); i++) {
                                    Date date = null;
                                    value = prmValuesData.get(i);
                                    if (value != null && !"".equals(value)) {
                                        date = Utils.getDateFromString(value, format);
                                    }
                                    prmValues.add(new SelectParamValue(date));
                                }
                                addParam4WherePart(stmt, operand, prmValues);
                            } else {
                                Date date = null;
                                if (value != null && !"".equals(value)) {
                                    date = Utils.getDateFromString(value, format);
                                }
                                addParam4WherePart(stmt, operand, date);
                            }
                            break;
                        }
                    }
                }
            }
        }

    }

    public void addParam4WherePart(String stmt, String paramValue) {
        this.addParam4WherePart(stmt, AND_OPERAND, paramValue);
    }

    public void addParam4WherePart(String stmt, Date paramValue) {
        this.addParam4WherePart(stmt, AND_OPERAND, paramValue);
    }

    public void addParam4WherePart(String stmt, Double paramValue) {
        this.addParam4WherePart(stmt, AND_OPERAND, paramValue);
    }

    public void addParam4WherePart(String stmt, Integer paramValue) {
        this.addParam4WherePart(stmt, AND_OPERAND, paramValue);
    }

    public void addParam4WherePart(String stmt, ArrayList<SelectParamValue> paramValues) {
        this.addParam4WherePart(stmt, AND_OPERAND, paramValues);
    }

    public void addParam4WherePart(String stmt, SelectParamValue... paramValues) {
        this.addParam4WherePart(stmt, AND_OPERAND, paramValues);
    }

    public void addParam4WherePart(String stmt, int paramType, AdvancedSearchParameterStatement paramValue) throws Exception {
        this.addParam4WherePart(stmt, AND_OPERAND, paramType, paramValue);
    }

    public void addParam4WherePart(String stmt, int paramType, AdvancedSearchParameterStatement paramValue, String format) throws Exception {
        this.addParam4WherePart(stmt, AND_OPERAND, paramType, paramValue, format);
    }

    public void addSelectParam(String paramValue) {
        SelectParamValue value = new SelectParamValue();
        value.setStringValue(paramValue);
        this.selectParams.add(value);
    }

    public void addSelectParam(Date paramValue) {
        SelectParamValue value = new SelectParamValue();
        value.setDateValue(paramValue);
        this.selectParams.add(value);

    }

    public void addSelectParam(Double paramValue) {
        SelectParamValue value = new SelectParamValue();
        value.setNumberValue(paramValue);
        this.selectParams.add(value);
    }

    public void addSelectParam(Long paramValue) {
        SelectParamValue value = new SelectParamValue();
        value.setLongValue(paramValue);
        this.selectParams.add(value);
    }

    public void addJsonColumn(String columnName) {
        jsonColumns.add(columnName);
    }

    public ArrayList<String> getJsonColumns() {
        return this.jsonColumns;
    }

    @Override
    public void setValues(PreparedStatement ps) throws SQLException {
        int idx = 1;
        for (int i = 0; i < selectParams.size(); i++) {
            SelectParamValue value = selectParams.get(i);
            value.setValue(ps, idx);
            idx++;
        }
        for (int i = 0; i < selectParams4WherePart.size(); i++) {
            SelectParam4WherePart sp = selectParams4WherePart.get(i);
            if (sp.isUsable()) {
                idx = sp.setParam(ps, idx);
            }
        }
    }

    public String getUsableParamsStr() {
        StringBuffer answer = new StringBuffer("PARAMS: \n");
        if (selectParams.size() > 0) {
            answer.append("  SELECT PARAMS: \n");
        }
        for (int i = 0; i < selectParams.size(); i++) {
            SelectParamValue param = selectParams.get(i);
            answer.append("    ");
            answer.append(param);
            answer.append("\n");
        }
        if (selectParams4WherePart.size() > 0) {
            answer.append("  WHERE PARAMS: \n");
        }
        for (int i = 0; i < selectParams4WherePart.size(); i++) {
            SelectParam4WherePart param = selectParams4WherePart.get(i);
            if (param.isUsable()) {
                answer.append("    ");
                answer.append(param);
                answer.append("\n");
            }
        }
        return answer.toString();
    }

    public String getStatement() {
        return statement;
    }

    public void setStatementGroupPart(String statementGroupPart) {
        this.statementGroupPart = statementGroupPart;
    }

    public String getStatementGroupPart() {
        return this.statementGroupPart;
    }

    public void setStatementOrderPart(String statementOrderPart) {
        this.statementOrderPart = statementOrderPart;
    }

    public String getStatementOrderPart() {
        return this.statementOrderPart;
    }

    public boolean isWhereExists() {
        return whereExists;
    }

    public void setWhereExists(boolean whereExists) {
        this.whereExists = whereExists;
    }

    public String getPredefinedConditionStetement() {
        return predefinedConditionStetement;
    }

    public void setPredefinedConditionStetement(String predefinedConditionStetement) {
        this.predefinedConditionStetement = predefinedConditionStetement;
    }

    public String getRecordCountLimitPart() {
        return recordCountLimitPart;
    }

    public void setRecordCountLimitPart(String recordCountLimitPart) {
        this.recordCountLimitPart = recordCountLimitPart;
    }

    public String logData() {
        StringBuffer answer = new StringBuffer();
        answer.append("==================== Select stmt (start) =============================\n");
        answer.append(getStatemenWithParams());
        answer.append("\n");
        answer.append("------------------------- Parameters -------------------------------------\n");
        answer.append(getUsableParamsStr());
        answer.append("\n");
        answer.append("==================== Select stmt end ================================\n");
        return answer.toString();
    }
}
