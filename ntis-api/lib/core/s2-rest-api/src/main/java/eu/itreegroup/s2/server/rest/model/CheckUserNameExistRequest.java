package eu.itreegroup.s2.server.rest.model;

import java.io.Serializable;
import lt.jmsys.spark.bind.annotations.Generated;

@Generated
public class CheckUserNameExistRequest implements Serializable {
	private final static long serialVersionUID = 1;

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public CheckUserNameExistRequest(String username) {
		super();
		this.username = username;
	}

	public CheckUserNameExistRequest() {
		super();
	}

	@Override
	public String toString() {
		return "LbcCheckUserNameExist [username=" + username + "]";
	}


}
