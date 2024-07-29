package eu.itreegroup.spark.modules.tasks.logic.forms.model;

public class SprTaskFileKey {

    public SprTaskFileKey() {

    }

    public SprTaskFileKey(String fil_key, Double fil_id, Double tfi_id, String fil_path) {
        super();
        this.fil_key = fil_key;
        this.fil_id = fil_id;
        this.tfi_id = tfi_id;
        this.fil_path = fil_path;
    }

    private String fil_key;

    private Double fil_id;

    private Double tfi_id;

    private String fil_path;

    public String getFil_key() {
        return fil_key;
    }

    public void setFil_key(String fil_key) {
        this.fil_key = fil_key;
    }

    public Double getFil_id() {
        return fil_id;
    }

    public void setFil_id(Double fil_id) {
        this.fil_id = fil_id;
    }

    public Double getTfi_id() {
        return tfi_id;
    }

    public void setTfi_id(Double tfi_id) {
        this.tfi_id = tfi_id;
    }

    public String getFil_path() {
        return fil_path;
    }

    public void setFil_path(String fil_path) {
        this.fil_path = fil_path;
    }

}
