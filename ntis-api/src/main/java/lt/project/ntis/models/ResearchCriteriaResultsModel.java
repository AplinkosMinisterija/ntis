package lt.project.ntis.models;

public class ResearchCriteriaResultsModel {

    private Double resId;

    private String display;

    private String code;

    private Double norm;

    private String normCompliance;

    private Double result;

    private Double rn_id;

    public Double getResId() {
        return resId;
    }

    public void setResId(Double resId) {
        this.resId = resId;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getNormCompliance() {
        return normCompliance;
    }

    public void setNormCompliance(String normCompliance) {
        this.normCompliance = normCompliance;
    }

    public Double getNorm() {
        return norm;
    }

    public void setNorm(Double norm) {
        this.norm = norm;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getRn_id() {
        return rn_id;
    }

    public void setRn_id(Double rn_id) {
        this.rn_id = rn_id;
    }

}
