package lt.project.ntis.models;

import java.util.ArrayList;

public class NtisPriorityFacilities{

    private Double org_id;
    
    private ArrayList<Double> wto_id;

	public Double getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Double org_id) {
		this.org_id = org_id;
	}

	public ArrayList<Double> getWto_id() {
	     return wto_id;
	}

	public void setWto_id(ArrayList<Double> wto_id) {
	     this.wto_id = wto_id;
	}

}
