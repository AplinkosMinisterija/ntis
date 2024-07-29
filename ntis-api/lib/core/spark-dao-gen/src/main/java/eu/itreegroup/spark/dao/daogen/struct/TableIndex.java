package eu.itreegroup.spark.dao.daogen.struct;

import java.util.ArrayList;

public class TableIndex {

    String name;

    ArrayList<TableColumn> columns;

    public TableIndex() {
        columns = new ArrayList<TableColumn>();
    }

    public TableIndex(String name) {
        columns = new ArrayList<TableColumn>();
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TableColumn> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<TableColumn> columns) {
        this.columns = columns;
    }

    public void addColumn(TableColumn column) {
        columns.add(column);
    }

    public boolean isPrimaryIndex() {
        return (columns.size() == 1 && columns.get(0).identifier);
    }

    @Override
    public String toString() {
        String answer = this.getName() + "\r\n";
        for (int i = 0; i < this.getColumns().size(); i++) {
            answer = answer + "           " + this.getColumns().get(i) + "\n";
        }
        return answer;
    }

}
