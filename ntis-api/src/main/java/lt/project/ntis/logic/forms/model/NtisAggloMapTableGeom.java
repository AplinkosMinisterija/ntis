package lt.project.ntis.logic.forms.model;

/**
 * Klasė skirta atiduoti aglomeracijų sluoksnio objekto informaciją į front-end
 */

public class NtisAggloMapTableGeom {

    private Integer id;

    private String geom;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }

}
