package eu.itreegroup.s2.server.rest.model;

import java.io.Serializable;
import lt.jmsys.spark.bind.annotations.Generated;

@Generated
public class RenewPasswordRequest implements Serializable {
	private final static long serialVersionUID = 1;

	private String email;
	private String token;


	public RenewPasswordRequest() {
		super();
	}
	public RenewPasswordRequest(String email, String token) {
		super();
		this.email = email;
		this.token = token;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "LbcRenewPasswordRequest [email=" + email + ", token=" + token + "]";
	}
}
