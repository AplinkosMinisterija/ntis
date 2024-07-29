package lt.project.ntis.models;

public class NtisAddressSuggestion {

    private Double id;

    private String address;

    private Double lksX;

    private Double lksY;

    public NtisAddressSuggestion() {
        super();
    }

    public NtisAddressSuggestion(Double id, String address, Double lksX, Double lksY) {
        super();
        this.id = id;
        this.address = address;
        this.lksX = lksX;
        this.lksY = lksY;
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLksX() {
        return lksX;
    }

    public void setLksX(Double lksX) {
        this.lksX = lksX;
    }

    public Double getLksY() {
        return lksY;
    }

    public void setLksY(Double lksY) {
        this.lksY = lksY;
    }

}
