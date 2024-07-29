package eu.itreegroup.spark.app.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import eu.itreegroup.spark.app.model.UserRole;

public class RoleManager implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6850170189503594682L;

    List<UserRole> userRoles;

    List<String> roleIndex;

    public RoleManager() {
        roleIndex = new ArrayList<String>();
        userRoles = new ArrayList<UserRole>();
    }

    public RoleManager(List<UserRole> userRoles) {
        roleIndex = new ArrayList<String>();
        if (userRoles == null) {
            userRoles = new ArrayList<UserRole>();
        } else {
            this.userRoles = userRoles;
            for (int i = 0; i < userRoles.size(); i++) {
                roleIndex.add(userRoles.get(i).getRol_code());
            }
        }
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public List<String> getRoleIndex() {
        return roleIndex;
    }

    public void setRoleIndex(List<String> roleIndex) {
        this.roleIndex = roleIndex;
    }

    public boolean userHasRole(String roleCode) {
        return roleIndex.contains(roleCode);
    }
}