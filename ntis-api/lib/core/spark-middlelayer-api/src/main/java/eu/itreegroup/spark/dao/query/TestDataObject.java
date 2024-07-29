package eu.itreegroup.spark.dao.query;

import java.util.Date;

public class TestDataObject {

    private Date dateParam;

    private boolean booleanParam;

    private String stringParam;

    private Double doubleParam;

    public Date getDateParam() {
        return dateParam;
    }

    public void setDateParam(Date dateParam) {
        this.dateParam = dateParam;
    }

    public boolean isBooleanParam() {
        return booleanParam;
    }

    public void setBooleanParam(boolean booleanParam) {
        this.booleanParam = booleanParam;
    }

    public String getStringParam() {
        return stringParam;
    }

    public void setStringParam(String stringParam) {
        this.stringParam = stringParam;
    }

    public Double getDoubleParam() {
        return doubleParam;
    }

    public void setDoubleParam(Double doubleParam) {
        this.doubleParam = doubleParam;
    }

    @Override
    public String toString() {
        return "dateParam: " + dateParam + " booleanParam:" + (booleanParam ? "true" : "false") + " stringParam: " + stringParam + " doubleParam: "
                + doubleParam;
    }

}
