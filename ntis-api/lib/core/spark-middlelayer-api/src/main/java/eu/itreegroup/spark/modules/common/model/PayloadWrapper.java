package eu.itreegroup.spark.modules.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class PayloadWrapper<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T payload;

    @JsonGetter("payload")
    public T get() {
        return payload;
    }

    @JsonSetter("payload")
    public void set(T payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "PayloadWrapper [payload=" + payload + "]";
    }

}
