package eu.itreegroup.spark.app.model;

import java.util.List;

import eu.itreegroup.s2.server.rest.model.Spr_paging_ot;

public class BrowseFormSearchResult<T> {

    private FormActions formActions;

    private List<T> data;

    private Spr_paging_ot paging;

    public BrowseFormSearchResult(List<T> data, Spr_paging_ot paging) {
        super();
        this.data = data;
        this.paging = paging;
    }

    public BrowseFormSearchResult(List<T> data, Spr_paging_ot paging, FormActions formActions) {
        super();
        this.data = data;
        this.paging = paging;
        this.formActions = formActions;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Spr_paging_ot getPaging() {
        return paging;
    }

    public void setPaging(Spr_paging_ot paging) {
        this.paging = paging;
    }

    public FormActions getFormActions() {
        return formActions;
    }

    public void setFormActions(FormActions formActions) {
        this.formActions = formActions;
    }

    @Override
    public String toString() {
        return "BrowseFormSearchResult [data=" + data + ", paging=" + paging + "]";
    }

}
