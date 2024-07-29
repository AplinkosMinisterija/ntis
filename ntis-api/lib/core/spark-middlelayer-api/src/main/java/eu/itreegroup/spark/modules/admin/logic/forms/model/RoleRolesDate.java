package eu.itreegroup.spark.modules.admin.logic.forms.model;

import java.util.Date;

public class RoleRolesDate {
    private String roa_id;
    private Date roa_date_to;
    private Date roa_date_from;
    
    public double parseRoa_idToDouble() {
        return Double.parseDouble(roa_id);
    }
        
    public String getRoa_id() {
        return roa_id;
    }
        
    public void setRoa_id(String roa_id) {
        this.roa_id = roa_id;
    }
     
    public Date getRoa_date_to() {
        return roa_date_to;
    }
     
    public void setRoa_date_to(Date roa_date_to) {
        this.roa_date_to = roa_date_to;
    }
    
    public Date getRoa_date_from() {
        return roa_date_from;
    }

    public void setRoa_date_from(Date roa_date_from) {
        this.roa_date_from = roa_date_from;
    }
     
    public String toString() {
        return  " roa_id: " + roa_id + " roa_date_to: " + roa_date_to + " roa_date_from: " + roa_date_from;      
    }

}
