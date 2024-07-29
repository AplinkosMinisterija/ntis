package lt.project.ntis.models;

import java.util.Date;

/**
 * Klasė skirta duomenų, reikalingų tyrimo normų atnaujinimui, perdavimui
 */
public class NtisResearchNormEditModel {

    private Double rn_id;

    private String rn_research_type;

    private Double rn_research_norm;

    private Date rn_date_from;

    private Date rn_date_to;

    private String rn_facility_installation_date;

    public Double getRn_id() {
        return rn_id;
    }

    public void setRn_id(Double rn_id) {
        this.rn_id = rn_id;
    }

    public String getRn_research_type() {
        return rn_research_type;
    }

    public void setRn_research_type(String rn_research_type) {
        this.rn_research_type = rn_research_type;
    }

    public Double getRn_research_norm() {
        return rn_research_norm;
    }

    public void setRn_research_norm(Double rn_research_norm) {
        this.rn_research_norm = rn_research_norm;
    }

    public Date getRn_date_from() {
        return rn_date_from;
    }

    public void setRn_date_from(Date rn_date_from) {
        this.rn_date_from = rn_date_from;
    }

    public Date getRn_date_to() {
        return rn_date_to;
    }

    public void setRn_date_to(Date rn_date_to) {
        this.rn_date_to = rn_date_to;
    }

    public String getRn_facility_installation_date() {
        return rn_facility_installation_date;
    }

    public void setRn_facility_installation_date(String rn_facility_installation_date) {
        this.rn_facility_installation_date = rn_facility_installation_date;
    }

}
