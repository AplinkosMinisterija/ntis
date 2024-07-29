package eu.itreegroup.spark.dto;

import java.util.List;

import eu.itreegroup.s2.server.rest.model.Spr_paging_ot;
import eu.itreegroup.spark.dao.common.SprModelBase;

/**
 * Class represents object for data, that will be used in browse form.
 * @param <T> represents DTO object (one record object, that will be presented in browse form table).
 */
public class BrowseData<T extends RecordBase> extends SprModelBase {

    /**
    * List of DTO objects
    */
    List<T> data;

    /**
     * Paging information. This information should be received from front end request.
     */
    Spr_paging_ot paging;

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

}
