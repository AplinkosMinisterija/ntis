package eu.itreegroup.spark.dao.query;

import org.json.JSONObject;

public class ColumnData {

    String columnName;

    String columnValue;

    boolean isNumber;

    boolean isJSONColumn;

    public ColumnData(String columnName, String columnValue, boolean isNumber, boolean isJSONColumn) {
        this.columnName = columnName;
        this.columnValue = columnValue;
        this.isNumber = isNumber;
        this.isJSONColumn = isJSONColumn;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public boolean isNumber() {
        return isNumber;
    }

    public void setNumber(boolean isNumber) {
        this.isNumber = isNumber;
    }

    public boolean isJSONColumn() {
        return isJSONColumn;
    }

    public void setJSONColumn(boolean isJSONColumn) {
        this.isJSONColumn = isJSONColumn;
    }

    @Override
    public String toString() {
        StringBuffer recAnswer = new StringBuffer();
        recAnswer.append("\"");
        recAnswer.append(columnName);
        recAnswer.append("\":");
        if (isJSONColumn) {
            recAnswer.append(columnValue);
        } else {
            if (isNumber) {
                if (columnValue == null || columnValue.equals("")) {
                    recAnswer.append("null");
                } else {
                    recAnswer.append(columnValue);
                }
            } else {
                recAnswer.append(JSONObject.quote(columnValue));
            }
        }
        return recAnswer.toString();
    }
}
