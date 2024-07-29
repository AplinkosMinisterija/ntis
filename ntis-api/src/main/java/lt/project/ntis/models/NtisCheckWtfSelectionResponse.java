package lt.project.ntis.models;

import java.util.ArrayList;

public class NtisCheckWtfSelectionResponse {

    private Boolean selected;

    private ArrayList<NtisCheckWtfSelectionResponseWtf> wtfs;

    public NtisCheckWtfSelectionResponse() {
        super();
    }

    public NtisCheckWtfSelectionResponse(Boolean selected, ArrayList<NtisCheckWtfSelectionResponseWtf> wtfs) {
        super();
        this.selected = selected;
        this.wtfs = wtfs;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public ArrayList<NtisCheckWtfSelectionResponseWtf> getWtfs() {
        return wtfs;
    }

    public void setWtfs(ArrayList<NtisCheckWtfSelectionResponseWtf> wtfs) {
        this.wtfs = wtfs;
    }

}
