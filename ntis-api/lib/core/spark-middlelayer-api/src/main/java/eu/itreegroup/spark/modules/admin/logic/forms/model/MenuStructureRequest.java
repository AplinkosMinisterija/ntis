package eu.itreegroup.spark.modules.admin.logic.forms.model;

public class MenuStructureRequest {

    private Double mst_id;

    private Double mst_mst_id;

    private Double mst_order;

    public Double getMst_id() {
        return mst_id;
    }

    public void setMst_id(Double mst_id) {
        this.mst_id = mst_id;
    }

    public Double getMst_mst_id() {
        return mst_mst_id;
    }

    public void setMst_mst_id(Double mst_mst_id) {
        this.mst_mst_id = mst_mst_id;
    }

    public Double getMst_order() {
        return mst_order;
    }

    public void setMst_order(Double mst_order) {
        this.mst_order = mst_order;
    }

    public String toString() {
        return "mst_id: " + mst_id.toString() + " mst_mst_id: " + mst_mst_id.toString() + " mst_order:" + mst_order.toString();
    }
}
