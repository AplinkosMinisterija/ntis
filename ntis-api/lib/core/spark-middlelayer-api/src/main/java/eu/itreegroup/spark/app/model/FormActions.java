package eu.itreegroup.spark.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class FormActions implements Serializable {

    private static final long serialVersionUID = 4937790646742834706L;

    private String formName;

    private HashSet<String> allActions;

    private HashSet<String> availableActions;

    private HashSet<String> disabledActions;

    private ArrayList<String> menuAvailableActions;

    public FormActions() {
        this.allActions = new HashSet<String>();
        this.availableActions = new HashSet<String>();
        this.disabledActions = new HashSet<String>();
        this.menuAvailableActions = new ArrayList<String>();
    }

    public FormActions(String formName, Collection<String> allActions, Collection<String> availableActions) {
        this.setFormName(formName);
        this.allActions = new HashSet<String>(allActions);
        this.availableActions = new HashSet<String>(availableActions);
        this.menuAvailableActions = new ArrayList<String>(availableActions);
        this.disabledActions = new HashSet<String>();
        manageDisabledActions();

    }

    public FormActions(String formName, Collection<String> availableActions) {
        this.setFormName(formName);
        this.availableActions = new HashSet<String>(availableActions);
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormName() {
        return this.formName;
    }

    public HashSet<String> getAllActions() {
        return this.allActions;
    }

    public HashSet<String> getAvailableActions() {
        return this.availableActions;
    }

    public HashSet<String> getDisabledActions() {
        return this.disabledActions;
    }

    public void addAction(String action) {
        this.allActions.add(action);
    }

    public void addAllActions(Collection<String> allActions) {
        this.allActions.addAll(allActions);
    }

    public void addAvailabelAction(String action) {
        this.availableActions.add(action);
    }

    public void addAllAvailabelAction(Collection<String> allAvailableActions) {
        this.availableActions.addAll(allAvailableActions);
    }

    public void manageDisabledActions() {
        this.disabledActions.addAll(this.allActions);
        this.disabledActions.removeAll(this.availableActions);
    }

    public boolean isActionAvailable(String action) {
        return this.availableActions.contains(action);
    }

    public void prepareAvailableMenuAction(String[] actions) {
        menuAvailableActions.clear();
        for (int i = 0; i < actions.length; i++) {
            if (availableActions.contains(actions[i])) {
                menuAvailableActions.add(actions[i]);
            }
        }
    }

    public ArrayList<String> getMenuAvailableActions() {
        return this.menuAvailableActions;
    }

    @Override
    public String toString() {
        return "Form: " + this.formName + "\r\n    allActions: " + this.allActions + ",\r\n    availableActions: " + this.availableActions
                + ",\r\n    disabledActions: " + this.disabledActions + "\r\n  menuAvailableActions: " + this.menuAvailableActions;
    }
}
