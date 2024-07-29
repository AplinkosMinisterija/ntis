package eu.itreegroup.spark.modules.tasks.logic.forms.model;

import java.util.ArrayList;

import eu.itreegroup.s2.server.rest.model.Spr_paging_ot;

public class SprTaskCardBrowsModel {

    ArrayList<SprTaskEditModel> data;

    public Spr_paging_ot pagingParams;

    public ArrayList<SprTaskEditModel> getData() {
        return data;
    }

    public void setData(ArrayList<SprTaskEditModel> data) {
        this.data = data;
    }

    public Spr_paging_ot getPagingParams() {
        return pagingParams;
    }

    public void setPagingParams(Spr_paging_ot pagingParams) {
        this.pagingParams = pagingParams;
    }

}
