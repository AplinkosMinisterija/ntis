package eu.itreegroup.s2.server.rest.model;

import java.io.Serializable;
import lt.jmsys.spark.bind.annotations.Generated;
import lt.jmsys.spark.bind.annotations.Length;

@Generated
public class Key_values_ot implements Serializable {
	private final static long serialVersionUID = 1;

	@Length(100)
	private String code;

	@Length(4000)
	private String key_value;

	@Length(4000)
	private String display_text;

	public Key_values_ot() {
	}

	public Key_values_ot(String code, String key_value, String display_text) {
		this.code = code;
		this.key_value = key_value;
		this.display_text = display_text;
	}

	public Key_values_ot(Key_values_ot src) {
		if (src == null) {
			return;
		}
		this.code = src.getCode();
		this.key_value = src.getKey_value();
		this.display_text = src.getDisplay_text();
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getKey_value() {
		return this.key_value;
	}

	public void setKey_value(String key_value) {
		this.key_value = key_value;
	}

	public String getDisplay_text() {
		return this.display_text;
	}

	public void setDisplay_text(String display_text) {
		this.display_text = display_text;
	}

	@Override
	public String toString() {
		return Key_values_ot.class.getName() + "@[" + "code = " + code + ", key_value = " + key_value
				+ ", display_text = " + display_text + "]";
	}

}
