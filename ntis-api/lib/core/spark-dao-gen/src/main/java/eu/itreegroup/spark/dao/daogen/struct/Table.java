package eu.itreegroup.spark.dao.daogen.struct;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;

public class Table {

    public String tableName;

    private String tableNameInDB;

    ArrayList<TableColumn> columns;

    ArrayList<TableIndex> ukIndexes;

    public boolean auditColumnExists = false;

    public String auditColumns = "REC_VERSION,REC_CREATE_TIMESTAMP,REC_TIMESTAMP,REC_USERID";

    public String flexFieldsList;

    public Table() {
        columns = new ArrayList<TableColumn>();
        ukIndexes = new ArrayList<TableIndex>();
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ArrayList<TableColumn> getColumns() {
        return this.columns;
    }

    public boolean isAuditColumnExists() {
        return auditColumnExists;
    }

    public void setAuditColumnExists(boolean auditColumnExists) {
        this.auditColumnExists = auditColumnExists;
    }

    public String getAuditColumns() {
        return auditColumns;
    }

    public void setAuditColumns(String auditColumns) {
        this.auditColumns = auditColumns;
    }

    public void setColumns(ArrayList<TableColumn> columns) {
        this.columns = columns;
    }

    public void setFlexFieldsList(String flexFieldList) {
        this.flexFieldsList = flexFieldList;
    }

    public String getFlexFieldList() {
        return this.flexFieldsList;
    }

    public ArrayList<TableIndex> getUkIndexes() {
        return ukIndexes;
    }

    public void setUkIndexes(ArrayList<TableIndex> ukIndexes) {
        this.ukIndexes = ukIndexes;
    }

    public String getTableNameInDB() {
        return tableNameInDB;
    }

    public void setTableNameInDB(String tableNameInDB) {
        this.tableNameInDB = tableNameInDB;
    }

    /**
     * Function will perform final check if provided column should be added to the list of columns that will be used for generation or not.
     * In case if column should be added function will return false (column not exists in Ignore list, otherwise true.
     * @param column - that should be checked it should be added to the column list or not 
     * @return false - column do not exists in ignore list, true - column exists in ignore list.
     */
    private boolean isColumnIgnored(TableColumn column) {
        return "tsvector".equalsIgnoreCase(column.getDbType()) || "geometry".equalsIgnoreCase(column.getDbType());
    }

    public void loadData(ResultSetMetaData metaData, String identifierName, String sequenceName, String identifierUpdatable) {
        int auditColumnCount = 0;
        try {
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                TableColumn column = new TableColumn();
                column.setDbName(metaData.getColumnName(i));
                column.setDbType(metaData.getColumnTypeName(i));
                column.setDbIsNullable(metaData.isNullable(i) == 1);
                if ("CLOB".equalsIgnoreCase(column.getDbType())) {
                    column.setDbPrecision(2000000000);
                } else {
                    column.setDbPrecision(metaData.getPrecision(i));
                }
                column.setDbScale(metaData.getScale(i));
                if (this.getFlexFieldList() != null && this.getFlexFieldList().indexOf("," + metaData.getColumnName(i).toUpperCase() + ",") != -1) {
                    column.setFlexField(true);
                } else {
                    column.setFlexField(false);
                }
                if (auditColumns.indexOf(metaData.getColumnName(i).toUpperCase()) != -1) {
                    auditColumnCount++;
                    column.setAuditColumn(true);
                } else {
                    column.setAuditColumn(false);
                }
                if (identifierName != null && identifierName.equalsIgnoreCase(metaData.getColumnName(i))) {
                    column.setIdentifier(true);
                    if (sequenceName != null && !"".equals(sequenceName)) {
                        column.setSequenceName(sequenceName);
                    }
                    if ("Y".equals(identifierUpdatable)) {
                        column.setUpdateble(true);
                    }
                } else {
                    column.setIdentifier(false);
                }
                if (!isColumnIgnored(column)) {
                    columns.add(column);
                }
            }
            if (auditColumnCount == 4) {
                this.setAuditColumnExists(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadUKIndexes(DatabaseMetaData dm, HashMap<String, String> indexList) {
        try {
            ResultSet rs = dm.getIndexInfo(null, null, this.getTableNameInDB(), true, true);
            String indexName = null;
            TableIndex tableIndex = null;
            while (rs.next()) {
                indexName = rs.getString("index_name");
                if (indexName != null) {
                    if (tableIndex == null) {
                        tableIndex = new TableIndex(indexName);
                    } else {
                        if (!indexName.equals(tableIndex.getName())) {
                            ukIndexes.add(tableIndex);
                            tableIndex = new TableIndex(indexName);
                        }
                    }
                    String columnName = rs.getString("column_name");
                    boolean columnFound = false;
                    for (int i = 0; (i < columns.size() && !columnFound); i++) {
                        if (columns.get(i).getDbName().equals(columnName)) {
                            columnFound = true;
                            tableIndex.addColumn(columns.get(i));
                        }
                    }
                }
            }
            if (tableIndex != null) {
                ukIndexes.add(tableIndex);
            }
            for (int i = ukIndexes.size() - 1; i >= 0; i--) {
                String indexType = indexList.get(ukIndexes.get(i).getName());
                if (!"U".equals(indexType)) {
                    ukIndexes.remove(i);
                }
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public String toString() {
        String answer = this.getTableName() + ":\r\n";
        for (int i = 0; i < this.getColumns().size(); i++) {
            answer = answer + "     " + this.getColumns().get(i) + "\n";
        }
        if (this.getUkIndexes().size() > 0) {
            answer = answer + " UK indexes: \r\n";
            for (int i = 0; i < this.getUkIndexes().size(); i++) {
                answer = answer + "     " + this.getUkIndexes().get(i) + "\n";
            }
        }
        return answer;
    }

}
