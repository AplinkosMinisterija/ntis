package lt.project.ntis.models;

public class ResearchRequestedCriteriaModel {

    private String display;

    private String code;

    private String belongs;

    private Boolean isSelected;
    
    private Double rn_id;

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBelongs() {
        return belongs;
    }

    public void setBelongs(String belongs) {
        this.belongs = belongs;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

	public Double getRn_id() {
		return rn_id;
	}

	public void setRn_id(Double rn_id) {
		this.rn_id = rn_id;
	}

}
