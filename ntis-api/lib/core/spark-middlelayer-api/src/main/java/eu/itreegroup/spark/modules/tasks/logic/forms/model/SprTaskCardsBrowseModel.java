package eu.itreegroup.spark.modules.tasks.logic.forms.model;

import java.util.ArrayList;

import eu.itreegroup.s2.server.rest.model.Spr_paging_ot;

public class SprTaskCardsBrowseModel {

    public ArrayList<SprTaskEditModel> data;

    public Spr_paging_ot paging;

    public ArrayList<SprTaskEditModel> getData() {
        return data;
    }

    public void setData(ArrayList<SprTaskEditModel> data) {
        this.data = data;
    }

    public Spr_paging_ot getPaging() {
        return paging;
    }

    public void setPaging(Spr_paging_ot pagingParams) {
        this.paging = pagingParams;
    }

}
