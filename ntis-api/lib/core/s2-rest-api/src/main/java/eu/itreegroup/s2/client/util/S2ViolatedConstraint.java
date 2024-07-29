package eu.itreegroup.s2.client.util;

import java.util.ArrayList;

public class S2ViolatedConstraint {

    private String name;

    private ArrayList<String> fields;

    public S2ViolatedConstraint() {
        super();
    }

    public S2ViolatedConstraint(String name, ArrayList<String> fields) {
        super();
        this.name = name;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getFields() {
        return fields;
    }

    public void setFields(ArrayList<String> fields) {
        this.fields = fields;
    }

}
