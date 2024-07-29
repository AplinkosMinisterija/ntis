package eu.itreegroup.s2.auth.viisp.model;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class ViispAuthData implements Serializable {

    private String personCode;

    private String firstName;

    private String lastName;

    private String companyCode;

    private String companyName;

    private String governmentEmployeeCode;

    private boolean exists;

    private boolean blocked;
    
    private Map<String, Object> customData;
    
    private String origin;
    
    private String ticket;

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGovernmentEmployeeCode() {
        return governmentEmployeeCode;
    }

    public void setGovernmentEmployeeCode(String governmentEmployeeCode) {
        this.governmentEmployeeCode = governmentEmployeeCode;
    }
    
	public Map<String, Object> getCustomData() {
		return customData;
	}

	public void setCustomData(Map<String, Object> customData) {
		this.customData = customData;
	}
	

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

    @Override
	public String toString() {
		return "ViispAuthData [personCode=" + personCode + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", companyCode=" + companyCode + ", companyName=" + companyName + ", governmentEmployeeCode="
				+ governmentEmployeeCode + ", exists=" + exists + ", blocked=" + blocked + ", customData=" + customData
				+ ", origin=" + origin + ", ticket=" + ticket + "]";
	}

    public String toDebugString() {
        return "AuthData [personCode=" + (null != personCode ? "******" : null) + ", firstName=" + firstName + ", lastName=" + lastName + ", companyCode="
                + companyCode + ", companyName=" + companyName + ", governmentEmployeeCode=" + governmentEmployeeCode + ", exists=" + exists + ", blocked="
                + blocked + "]";
    }

    public String toInfoString() {
        return "AuthData [personCode=" + getPartPersonCode(personCode) + ", firstName=" + firstName + ", lastName=" + lastName + ", companyCode=" + companyCode
                + ", geCode=" + governmentEmployeeCode + "]";
    }

    private static String getPartPersonCode(String code) {
        try {
            if (null == code) {
                return null;
            } else if (code.length() <= 4) {
                return "****".substring(0, code.length());
            } else {
                int length = code.length() - 4;
                return "***********".substring(0, length) + code.substring(length);
            }
        } catch (Throwable e) {
            return "*0*";
        }
    }





}
