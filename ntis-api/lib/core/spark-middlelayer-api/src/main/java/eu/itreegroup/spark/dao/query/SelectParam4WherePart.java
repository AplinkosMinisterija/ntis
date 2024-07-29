package eu.itreegroup.spark.dao.query;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectParam4WherePart {

    private static final Logger log = LoggerFactory.getLogger(SelectParam4WherePart.class);

    private String selectStmtPart;

    private String operand;

    private boolean usable;

    private List<SelectParamValue> params;

    public SelectParam4WherePart() {
        this(false);
    }

    public SelectParam4WherePart(Boolean usable) {
        params = new ArrayList<SelectParamValue>();
        this.usable = usable;
    }

    public String getSelectStmtPart() {
        return selectStmtPart;
    }

    public void setSelectStmtPart(String selectStmtPart) {
        this.selectStmtPart = selectStmtPart;
    }

    public List<SelectParamValue> getParams() {
        return params;
    }

    public void setParams(List<SelectParamValue> params) {
        this.params = params;
        if (params != null && params.size() > 0) {
            for (int i = 0; i < params.size(); i++) {
                SelectParamValue pv = params.get(i);
                if (pv.getValueType() != SelectParamValue.NONE_VALUE) {
                    this.setUsable(true);
                }
            }
        }
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }

    public boolean isUsable() {
        return this.usable;
    }

    public void addParam(SelectParamValue paramValue) {
        log.trace("Will try add SelectParamValue: " + paramValue.toString());
        this.params.add(paramValue);
        if (paramValue.getValueType() != SelectParamValue.NONE_VALUE) {
            this.setUsable(true);
        }
    }

    public void addParam(String paramValue) {
        log.trace("Will try add parameter with value: " + paramValue);
        SelectParamValue prmVal = new SelectParamValue();
        prmVal.setStringValue(paramValue);
        this.params.add(prmVal);
        if (prmVal.getValueType() != SelectParamValue.NONE_VALUE) {
            this.setUsable(true);
        }
    }

    public void addParam(Double paramValue) {
        log.trace("Will try add parameter with value: " + (paramValue != null ? paramValue.toString() : "null"));
        SelectParamValue prmVal = new SelectParamValue();
        prmVal.setNumberValue(paramValue);
        this.params.add(prmVal);
        if (prmVal.getValueType() != SelectParamValue.NONE_VALUE) {
            this.setUsable(true);
        }
    }

    public void addParam(double paramValue) {
        this.addParam(Double.valueOf(paramValue));
    }

    public void addParam(Integer paramValue) {
        this.addParam(paramValue.doubleValue());
    }

    public void addParam(Date paramValue) {
        log.trace("Will try add parameter with value: " + paramValue);
        SelectParamValue prmVal = new SelectParamValue();
        prmVal.setDateValue(paramValue);
        this.params.add(prmVal);
        if (prmVal.getValueType() != SelectParamValue.NONE_VALUE) {
            this.setUsable(true);
        }
    }

    public int setParam(PreparedStatement stmt, int idx) {
        for (int i = 0; i < params.size(); i++) {
            SelectParamValue pv = params.get(i);
            if (pv.getValueType() != SelectParamValue.NONE_VALUE) {
                pv.setValue(stmt, idx);
                idx++;
            }
        }
        return idx;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    public String getOperand() {
        return this.operand;
    }

    @Override
    public String toString() {
        return "selectStmtPart: " + selectStmtPart + " operand: " + operand + " is usable: " + (usable ? "TRUE" : "FALSE") + " params: " + params;
    }
}
