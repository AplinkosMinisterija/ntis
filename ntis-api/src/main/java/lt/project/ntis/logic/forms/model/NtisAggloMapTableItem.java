package lt.project.ntis.logic.forms.model;

/**
 * Klasė skirta atiduoti aglomeracijų sluoksnio objekto informaciją į front-end
 */

public class NtisAggloMapTableItem {

    private Integer id;

    private String name;

    private Integer municipalityCode;

    private Integer populationEquivalent;

    private Integer populationDensity;

    private Double area;

    private String docName;

    private String docNo;

    private String docDate;

    private Double minX;

    private Double minY;

    private Double maxX;

    private Double maxY;

    public NtisAggloMapTableItem() {
        super();
    }

    public NtisAggloMapTableItem(Integer id, String name, Integer municipalityCode, Integer populationEquivalent, Integer populationDensity, Double area,
            String docName, String docNo, String docDate, Double minX, Double minY, Double maxX, Double maxY) {
        super();
        this.id = id;
        this.name = name;
        this.municipalityCode = municipalityCode;
        this.populationEquivalent = populationEquivalent;
        this.populationDensity = populationDensity;
        this.area = area;
        this.docName = docName;
        this.docNo = docNo;
        this.docDate = docDate;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMunicipalityCode() {
        return municipalityCode;
    }

    public void setMunicipalityCode(Integer municipalityCode) {
        this.municipalityCode = municipalityCode;
    }

    public Integer getPopulationEquivalent() {
        return populationEquivalent;
    }

    public void setPopulationEquivalent(Integer populationEquivalent) {
        this.populationEquivalent = populationEquivalent;
    }

    public Integer getPopulationDensity() {
        return populationDensity;
    }

    public void setPopulationDensity(Integer populationDensity) {
        this.populationDensity = populationDensity;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public Double getMinX() {
        return minX;
    }

    public void setMinX(Double minX) {
        this.minX = minX;
    }

    public Double getMinY() {
        return minY;
    }

    public void setMinY(Double minY) {
        this.minY = minY;
    }

    public Double getMaxX() {
        return maxX;
    }

    public void setMaxX(Double maxX) {
        this.maxX = maxX;
    }

    public Double getMaxY() {
        return maxY;
    }

    public void setMaxY(Double maxY) {
        this.maxY = maxY;
    }

}
