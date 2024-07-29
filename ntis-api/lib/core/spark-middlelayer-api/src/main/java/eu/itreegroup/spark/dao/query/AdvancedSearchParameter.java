package eu.itreegroup.spark.dao.query;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AdvancedSearchParameter {

    public static final String LOWERCASE = "lowercase";

    public static final String UPPERCASE = "uppercase";

    public static final String REGURAL = "regular";

    public static final String REGULAR_LATIN = "regularLatin";

    public static final String CASE_INSENSITIVE_LATIN = "caseInsensitiveLatin";

    private String condition;

    private String value;

    private List<String> values;

    private String upperLower;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuffer answer = new StringBuffer();
        answer.append("{condition: ");
        answer.append(condition);
        answer.append(" value: ");
        if (values != null) {
            answer.append(values.toString());
        } else {
            answer.append(value);
        }
        return answer.toString();
    }

    public String getUpperLower() {
        return upperLower;
    }

    public void setUpperLower(String upperLower) {
        this.upperLower = upperLower;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<String> getValues() {
        return this.values;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return (value == null || "".equals(value)) && (values == null || values.size() == 0);
    }

}
