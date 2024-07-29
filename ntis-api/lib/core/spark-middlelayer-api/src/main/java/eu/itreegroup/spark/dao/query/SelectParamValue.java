package eu.itreegroup.spark.dao.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectParamValue {

    private static final Logger log = LoggerFactory.getLogger(SelectParamValue.class);

    static public final int NONE_VALUE = 0;

    static public final int STRING_VALUE = 1;

    static public final int NUMBER_VALUE = 2;

    static public final int DATE_VALUE = 3;

    static public final int LONG_VALUE = 4;

    private int valueType;

    private String stringValue;

    private Date dateValue;

    private Double numberValue;

    private Long longValue;

    private int nullValueType;

    public SelectParamValue() {
        this.setValueType(SelectParamValue.NONE_VALUE);
    }

    public SelectParamValue(String stringValue) {
        setStringValue(stringValue);
    }

    public SelectParamValue(Date dateValue) {
        setDateValue(dateValue);
    }

    public SelectParamValue(Double numberValue) {
        setNumberValue(numberValue);
    }

    public SelectParamValue(Long longValue) {
        setLongValue(longValue);
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        if (stringValue == null || "".equals(stringValue)) {
            this.setValueType(SelectParamValue.NONE_VALUE);
            this.nullValueType = java.sql.Types.VARCHAR;
        } else {
            this.setValueType(SelectParamValue.STRING_VALUE);
        }
        this.stringValue = stringValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        if (dateValue == null) {
            this.setValueType(SelectParamValue.NONE_VALUE);
            this.nullValueType = java.sql.Types.TIMESTAMP;
        } else {
            this.setValueType(SelectParamValue.DATE_VALUE);
        }
        this.dateValue = dateValue;
    }

    public Double getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(Double numberValue) {
        if (numberValue == null) {
            this.setValueType(SelectParamValue.NONE_VALUE);
            this.nullValueType = java.sql.Types.NUMERIC;
        } else {
            this.setValueType(SelectParamValue.NUMBER_VALUE);
        }
        this.numberValue = numberValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        if (longValue == null) {
            this.setValueType(SelectParamValue.NONE_VALUE);
            this.nullValueType = java.sql.Types.BIGINT;
        } else {
            this.setValueType(SelectParamValue.LONG_VALUE);
        }
        this.longValue = longValue;
    }

    public void setValue(PreparedStatement stmt, int idx) {
        try {
            if (this.valueType == SelectParamValue.STRING_VALUE) {
                log.trace("Set value for prepared statement using method: setString(" + idx + ", " + this.getStringValue() + ")");
                stmt.setString(idx, this.getStringValue());
            } else {
                if (this.valueType == SelectParamValue.NUMBER_VALUE) {
                    log.trace("Set value for prepared statement using method: setDouble(" + idx + ", " + this.getNumberValue() + ")");
                    stmt.setDouble(idx, this.getNumberValue());
                } else if (this.valueType == SelectParamValue.LONG_VALUE) {
                    log.trace("Set value for prepared statement using method: setInt(" + idx + ", " + this.getLongValue() + ")");
                    stmt.setLong(idx, this.getLongValue());
                } else {
                    if (this.valueType == SelectParamValue.DATE_VALUE) {
                        log.trace("Set value for prepared statement using method: setDate(" + idx + ", " + this.getDateValue().getTime() + ")");
                        stmt.setTimestamp(idx, new java.sql.Timestamp(this.getDateValue().getTime()));
                    } else {
                        if (this.valueType == SelectParamValue.NONE_VALUE) {
                            log.trace("Set value for prepared statement using method: setNull(" + idx + ", " + null + ")");
                            stmt.setNull(idx, this.nullValueType);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            log.error("Error during set parameter to the prepared statement", e);
        }
    }

    @Override
    public String toString() {
        switch (valueType) {
            case (NONE_VALUE):
                return " valueType: NONE ";
            case (STRING_VALUE):
                return " valueType: STRING, value: " + stringValue;
            case (NUMBER_VALUE):
                return " valueType: NUMNER, value: " + numberValue;
            case (DATE_VALUE):
                return " valueType: DATE, value: " + dateValue;
            case (LONG_VALUE):
                return " valueType: LONG, value: " + longValue;
            default:
                return " valueType: NONE ";
        }
    }

}
