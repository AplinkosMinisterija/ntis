package eu.itreegroup.spark.dao.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class RecordData {

    List<ColumnData> record;

    Map<String, Integer> indexCache;

    public RecordData() {
        this.record = new ArrayList<ColumnData>();
        indexCache = new HashMap<String, Integer>();
    }

    /**
     * Method will store column data in inner structure
     * @param columnData - data that should be stored in record object
     */
    public void add(ColumnData columnData) {
        record.add(columnData);
    }

    /**
     * Method will clear stored data (data related with column). This method will not clear cached information.
     */
    public void clearData() {
        record.clear();
    }

    /**
     * Method will clear all stored information including cached information.
     */
    public void clearAll() {
        clearData();
        indexCache.clear();
    }

    /**
     * Will return all stored information related with columns data. 
     * @return
     */
    public List<ColumnData> getRecord() {
        return this.record;
    }

    /**
     * Will return column data by provided column name.In case if such column not found function will return null.
     * @param columnName
     * @return
     */
    public ColumnData getColumnByName(String columnName) {
        if (columnName != null) {
            Integer idx = indexCache.get(columnName);
            if (idx != null) {
                return record.get(idx);
            } else {
                for (int i = 0; i < record.size(); i++) {
                    ColumnData column = record.get(i);
                    if (columnName.equalsIgnoreCase(column.getColumnName())) {
                        indexCache.put(columnName, i);
                        return column;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Function will construct record json representation.
     */
    @Override
    public String toString() {
        StringBuffer recAnswer = new StringBuffer();
        int i = 0;
        for (ColumnData column : record) {
            recAnswer.append("\"");
            recAnswer.append(column.getColumnName());
            recAnswer.append("\":");
            if (column.isJSONColumn()) {
                recAnswer.append(column.getColumnValue());
            } else {
                if (column.isNumber()) {
                    if (column.getColumnValue() == null || column.getColumnValue().equals("")) {
                        recAnswer.append("null");
                    } else {
                        recAnswer.append(column.getColumnValue());
                    }
                } else {
                    recAnswer.append(JSONObject.quote(column.getColumnValue()));
                }
            }
            if (++i < record.size()) {
                recAnswer.append(", ");
            }
        }
        return recAnswer.toString();
    }
}
