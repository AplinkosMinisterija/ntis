package lt.project.ntis.models;

import java.util.ArrayList;
import java.util.List;

public class NtisMapPoint {

    private Integer id;

    private List<Double> coordinates;

    public NtisMapPoint() {
        super();
    }

    public NtisMapPoint(Integer id, List<Double> coordinates) {
        super();
        this.id = id;
        this.coordinates = coordinates;
    }

    public NtisMapPoint(Integer id, Double x, Double y) {
        super();
        this.id = id;
        this.coordinates = new ArrayList<>();
        this.coordinates.add(x);
        this.coordinates.add(y);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

}
