package lt.project.ntis.models.api;

public class ApiServicedObject {

    private Double servicedObjectId;

    private String servicedObjectAddressLine;

    private Double servicedObjectLatitude;

    private Double servicedObjectLongitude;

    private String servicedObjectPurpose;

    public Double getServicedObjectId() {
        return servicedObjectId;
    }

    public void setServicedObjectId(Double servicedObjectId) {
        this.servicedObjectId = servicedObjectId;
    }

    public String getServicedObjectAddressLine() {
        return servicedObjectAddressLine;
    }

    public void setServicedObjectAddressLine(String servicedObjectAddressLine) {
        this.servicedObjectAddressLine = servicedObjectAddressLine;
    }

    public Double getServicedObjectLatitude() {
        return servicedObjectLatitude;
    }

    public void setServicedObjectLatitude(Double servicedObjectLatitude) {
        this.servicedObjectLatitude = servicedObjectLatitude;
    }

    public Double getServicedObjectLongitude() {
        return servicedObjectLongitude;
    }

    public void setServicedObjectLongitude(Double servicedObjectLongitude) {
        this.servicedObjectLongitude = servicedObjectLongitude;
    }

    public String getServicedObjectPurpose() {
        return servicedObjectPurpose;
    }

    public void setServicedObjectPurpose(String servicedObjectPurpose) {
        this.servicedObjectPurpose = servicedObjectPurpose;
    }

}
