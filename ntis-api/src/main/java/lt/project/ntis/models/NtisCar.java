package lt.project.ntis.models;

public class NtisCar {

    private Integer id;

    private String regNo;

    private String model;

    private Double capacity;

    private Double tubeLength;

    private Boolean isInUse;

    private String carType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public Double getTubeLength() {
        return tubeLength;
    }

    public void setTubeLength(Double tubeLength) {
        this.tubeLength = tubeLength;
    }

    public Boolean getIsInUse() {
        return isInUse;
    }

    public void setIsInUse(Boolean isInUse) {
        this.isInUse = isInUse;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    @Override
    public String toString() {
        return "NtisCar [id=" + id + ", regNo=" + regNo + ", model=" + model + ", capacity=" + capacity + ", tubeLength=" + tubeLength + ", isInUse=" + isInUse
                + ", carType=" + carType + "]";
    }

}
