package lt.project.ntis.logic.forms.model;

import java.util.ArrayList;

/**
 * Klasė skirta talpinti žemėlapio lentelės duomenis
 */

public class NtisMapTableResult<T> {

    private ArrayList<T> items;

    private Integer totalItems;

    private Integer filteredItems;

    public NtisMapTableResult() {
        super();
    }

    public NtisMapTableResult(ArrayList<T> items, Integer totalItems, Integer filteredItems) {
        super();
        this.items = items;
        this.totalItems = totalItems;
        this.filteredItems = filteredItems;
    }

    public ArrayList<T> getItems() {
        return items;
    }

    public void setItems(ArrayList<T> items) {
        this.items = items;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(Integer filteredItems) {
        this.filteredItems = filteredItems;
    }

}
