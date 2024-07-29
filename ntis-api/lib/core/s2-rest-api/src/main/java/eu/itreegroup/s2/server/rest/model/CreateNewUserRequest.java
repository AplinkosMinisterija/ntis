package eu.itreegroup.s2.server.rest.model;

import java.io.Serializable;
import lt.jmsys.spark.bind.annotations.Generated;

@Generated
public class CreateNewUserRequest implements Serializable {
	private final static long serialVersionUID = 1;

	private String username;
	private String password;



	public CreateNewUserRequest() {
		super();
	}

	public CreateNewUserRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LbcCreateNewUser [username=" + username + ", password=" + password + "]";
	}
}
