package eu.itreegroup.spark.modules.common.rest.model;

import eu.itreegroup.s2.server.rest.model.WebSessionInfo;

public class SprBackendWebSessionInfo extends WebSessionInfo {

    protected String roleType;

    protected String userType;

    protected String sessionKey;

    public SprBackendWebSessionInfo(SprBackendUserSession src) {
        super(src.getPerson_name(), src.getPerson_surname(), src.getUsername(), src.getRole(), src.getSes_rol_id(), src.getRole_name(), src.getSes_org_id(),
                src.getOrg_name(), src.getSes_language(), src.getUserPasswordChangeToken(), src.getUsr_terms_accepted(), src.getDefaultRoute());
        // TODO: Reiktų sutvarkyti, kad ne rolė būtų
        this.setRoleType(src.getSes_usr_type());
        this.setUserType(src.getSes_usr_type());
        this.setSessionKey(src.getSes_key());
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

}
