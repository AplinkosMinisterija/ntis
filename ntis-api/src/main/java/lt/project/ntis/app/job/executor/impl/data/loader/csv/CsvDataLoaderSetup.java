package lt.project.ntis.app.job.executor.impl.data.loader.csv;

import java.util.ArrayList;

public class CsvDataLoaderSetup {

    ArrayList<String> csvFileDataColumns;

    String dataCopyInstructionPart;

    ArrayList<String> statementParamNames;

    String insertStatement;

    String fileEncoding;

    public CsvDataLoaderSetup() {
        csvFileDataColumns = new ArrayList<String>();
        statementParamNames = new ArrayList<String>();
    }

    public void addCsvFieldDataColumn(String columnName) {
        csvFileDataColumns.add(columnName);
    }

    public void addStatementParamName(String columnName) {
        statementParamNames.add(columnName);
    }

    public ArrayList<String> getCsvFileDataColumns() {
        return csvFileDataColumns;
    }

    public void setCsvFileDataColumns(ArrayList<String> csvFileDataColumns) {
        this.csvFileDataColumns = csvFileDataColumns;
    }

    public String getDataCopyInstructionPart() {
        return dataCopyInstructionPart;
    }

    public void setDataCopyInstructionPart(String dataCopyInstructionPart) {
        this.dataCopyInstructionPart = dataCopyInstructionPart;
    }

    public ArrayList<String> getStatementParamNames() {
        return statementParamNames;
    }

    public void setStatementParamNames(ArrayList<String> statementParamNames) {
        this.statementParamNames = statementParamNames;
    }

    public String getInsertStatement() {
        return insertStatement;
    }

    public void setInsertStatement(String insertStatement) {
        this.insertStatement = insertStatement;
    }

    public String constructCsvInsertFields() {
        String answer = "";
        for (int i = 0; i < csvFileDataColumns.size(); i++) {
            answer = answer + csvFileDataColumns.get(i) + " text";
            if (i + 1 < csvFileDataColumns.size()) {
                answer = answer + ", ";
            }
        }
        return answer;
    }

    public String constructCsvCopyFields() {
        String answer = "(";
        for (int i = 0; i < csvFileDataColumns.size(); i++) {
            answer = answer + csvFileDataColumns.get(i);
            if (i + 1 < csvFileDataColumns.size()) {
                answer = answer + ", ";
            } else {
                answer = answer + ")";
            }
        }
        return answer;
    }

    public String getFileEncoding() {
        return fileEncoding;
    }

    public void setFileEncoding(String fileEncoding) {
        this.fileEncoding = fileEncoding;
    }

    @Override
    public String toString() {
        return "csvFileDataColumns: " + csvFileDataColumns + " dataCopyInstructionPart: " + dataCopyInstructionPart + " insertStatement: " + insertStatement
                + " statementParamNames: " + statementParamNames + " fileEncoding: " + fileEncoding;
    }

}
