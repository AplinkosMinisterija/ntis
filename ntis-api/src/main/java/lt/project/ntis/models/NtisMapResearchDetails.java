package lt.project.ntis.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import eu.itreegroup.spark.annotations.TypeScriptOptional;

@JsonInclude(Include.NON_NULL)
public class NtisMapResearchDetails {

    @TypeScriptOptional
    private Double id;

    @TypeScriptOptional
    private Double wtfId;

    @TypeScriptOptional
    private String address;

    @TypeScriptOptional
    private String aDate;

    @TypeScriptOptional
    private String aComplianceName;

    @TypeScriptOptional
    private Double aValue;

    @TypeScriptOptional
    private Double aNorm;

    @TypeScriptOptional
    private String bDate;

    @TypeScriptOptional
    private String bComplianceName;

    @TypeScriptOptional
    private Double bValue;

    @TypeScriptOptional
    private Double bNorm;

    @TypeScriptOptional
    private String cDate;

    @TypeScriptOptional
    private String cComplianceName;

    @TypeScriptOptional
    private Double cValue;

    @TypeScriptOptional
    private Double cNorm;

    @TypeScriptOptional
    private String dDate;

    @TypeScriptOptional
    private String dComplianceName;

    @TypeScriptOptional
    private Double dValue;

    @TypeScriptOptional
    private Double dNorm;

    @TypeScriptOptional
    private String link;

    @TypeScriptOptional
    private Double x;

    @TypeScriptOptional
    private Double y;

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public Double getWtfId() {
        return wtfId;
    }

    public void setWtfId(Double wtfId) {
        this.wtfId = wtfId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getaDate() {
        return aDate;
    }

    public void setaDate(String aDate) {
        this.aDate = aDate;
    }

    public String getaComplianceName() {
        return aComplianceName;
    }

    public void setaComplianceName(String aComplianceName) {
        this.aComplianceName = aComplianceName;
    }

    public Double getaValue() {
        return aValue;
    }

    public void setaValue(Double aValue) {
        this.aValue = aValue;
    }

    public Double getaNorm() {
        return aNorm;
    }

    public void setaNorm(Double aNorm) {
        this.aNorm = aNorm;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }

    public String getbComplianceName() {
        return bComplianceName;
    }

    public void setbComplianceName(String bComplianceName) {
        this.bComplianceName = bComplianceName;
    }

    public Double getbValue() {
        return bValue;
    }

    public void setbValue(Double bValue) {
        this.bValue = bValue;
    }

    public Double getbNorm() {
        return bNorm;
    }

    public void setbNorm(Double bNorm) {
        this.bNorm = bNorm;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }

    public String getcComplianceName() {
        return cComplianceName;
    }

    public void setcComplianceName(String cComplianceName) {
        this.cComplianceName = cComplianceName;
    }

    public Double getcValue() {
        return cValue;
    }

    public void setcValue(Double cValue) {
        this.cValue = cValue;
    }

    public Double getcNorm() {
        return cNorm;
    }

    public void setcNorm(Double cNorm) {
        this.cNorm = cNorm;
    }

    public String getdDate() {
        return dDate;
    }

    public void setdDate(String dDate) {
        this.dDate = dDate;
    }

    public String getdComplianceName() {
        return dComplianceName;
    }

    public void setdComplianceName(String dComplianceName) {
        this.dComplianceName = dComplianceName;
    }

    public Double getdValue() {
        return dValue;
    }

    public void setdValue(Double dValue) {
        this.dValue = dValue;
    }

    public Double getdNorm() {
        return dNorm;
    }

    public void setdNorm(Double dNorm) {
        this.dNorm = dNorm;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

}
