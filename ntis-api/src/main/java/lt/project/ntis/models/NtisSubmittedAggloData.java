package lt.project.ntis.models;

import java.util.ArrayList;
import java.util.List;

public class NtisSubmittedAggloData {

    private Double id;

    private String municipality;

    private String confirmDate;

    private String confirmDocNo;

    private String statusCode;

    private String status;

    private ArrayList<NtisSubmittedAggloDataVersion> versions;

    private ArrayList<String> lastCheckReport;

    private List<NtisSubmittedAggloDataGeom> geoms;

    public NtisSubmittedAggloData() {
        super();
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getConfirmDocNo() {
        return confirmDocNo;
    }

    public void setConfirmDocNo(String confirmDocNo) {
        this.confirmDocNo = confirmDocNo;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<NtisSubmittedAggloDataVersion> getVersions() {
        return versions;
    }

    public void setVersions(ArrayList<NtisSubmittedAggloDataVersion> versions) {
        this.versions = versions;
    }

    public ArrayList<String> getLastCheckReport() {
        return lastCheckReport;
    }

    public void setLastCheckReport(ArrayList<String> lastCheckReport) {
        this.lastCheckReport = lastCheckReport;
    }

    public List<NtisSubmittedAggloDataGeom> getGeoms() {
        return geoms;
    }

    public void setGeoms(List<NtisSubmittedAggloDataGeom> geoms) {
        this.geoms = geoms;
    }

}
