package eu.itreegroup.spark.dto;

import java.util.List;

/**
 * Represent common information of DTO object
 */
public class RecordBase {

    /**
     * number that presents record in common list
     */
    Long skip;

    /**
     * List of available actions for current record
     */
    List<String> availableActions;

    public Long getSkip() {
        return skip;
    }

    public void setSkip(Long skip) {
        this.skip = skip;
    }

    public List<String> getAvailableActions() {
        return availableActions;
    }

    public void setAvailableActions(List<String> availableActions) {
        this.availableActions = availableActions;
    }

}
