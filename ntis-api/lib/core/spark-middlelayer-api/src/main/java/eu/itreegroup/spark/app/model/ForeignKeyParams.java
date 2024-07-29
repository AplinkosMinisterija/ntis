package eu.itreegroup.spark.app.model;

public class ForeignKeyParams {

    String filterValue;

    Double recordCount;

    public String getFilterValue() {
        return filterValue;
    }

    /**
     * Function prepare string for search statement. Function will remove whitespace and convert to upper case.
     * @return modified string for search
     */
    public String getFilterValueModified4Search() {
        if (getFilterValue() != null) {
            return getFilterValue().toUpperCase().replaceAll("\\s", "");
        } else {
            return null;
        }
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public Double getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Double recordCount) {
        this.recordCount = recordCount;
    }

}
