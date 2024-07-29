package eu.itreegroup.s2.server.rest.model;

import java.io.Serializable;
import lt.jmsys.spark.bind.annotations.Generated;

@Generated
public class NewPasswordRequest implements Serializable {
	private final static long serialVersionUID = 1;

	private String email;

	public NewPasswordRequest() {
		super();
	}

	public NewPasswordRequest(String email) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "LbcNewPasswordRequest [email=" + email + "]";
	}

}
