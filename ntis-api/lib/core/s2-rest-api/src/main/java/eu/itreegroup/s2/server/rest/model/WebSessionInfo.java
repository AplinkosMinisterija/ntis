package eu.itreegroup.s2.server.rest.model;

/**
 * Proxy class for UI
 * 
 *
 */
public class WebSessionInfo {

    protected String personName;

    protected String personLastName;

    protected String userName;

    protected String roleCode;

    protected Double roleId;

    protected String roleName;

    protected Double orgId;

    protected String orgName;

    protected String language;

    protected String usrPasswordChangeToken;

    protected String usrTermsAccepted;

    protected String defaultRoute;

    /**
     * Create a WebSessionInfo object
     * @deprecated
     * This constructor is deprecated as it is missing Double roleId parameter.
     * <p> Use {@link WebSessionInfo#WebSessionInfo(String, String, String, String, Double, String, String, String, String, String)} instead.
     */
    @Deprecated
    public WebSessionInfo(String personName, String personLastName, String userName, String roleCode, String roleName, String language,
            String usrPasswordChangeToken, String usrTermsAccepted, String defaultRoute) {
        super();
        this.personName = personName;
        this.personLastName = personLastName;
        this.userName = userName;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.language = language;
        this.usrPasswordChangeToken = usrPasswordChangeToken;
        this.usrTermsAccepted = usrTermsAccepted;
        this.defaultRoute = defaultRoute;
    }

    /**
     * Create a WebSessionInfo object
     * @deprecated
     * This constructor is deprecated as it is missing Double orgId and orgName parameters.
     * <p> Use {@link WebSessionInfo#WebSessionInfo(String, String, String, String, Double, String, String, String, String, String, Double, String)} instead.
     */
    @Deprecated
    public WebSessionInfo(String personName, String personLastName, String userName, String roleCode, Double roleId, String roleName, String language,
            String usrPasswordChangeToken, String usrTermsAccepted, String defaultRoute) {
        super();
        this.personName = personName;
        this.personLastName = personLastName;
        this.userName = userName;
        this.roleCode = roleCode;
        this.roleId = roleId;
        this.roleName = roleName;
        this.language = language;
        this.usrPasswordChangeToken = usrPasswordChangeToken;
        this.usrTermsAccepted = usrTermsAccepted;
        this.defaultRoute = defaultRoute;
    }

    /**
     * Creates session object. This object is used in front-end application.
     * @param personName - logged in person name
     * @param personLastName - logged in person surname
     * @param userName - logged in person application name (user name)
     * @param role - role code, which is currently assigned to the logged in user
     * @param roleId - role internal id, which is currently assigned to the logged in user
     * @param roleName - role name (description), which is currently assigned to the logged in user
     * @param orgId - organization id which is assigned to the logged in user
     * @param orgName - organization name which is assigned to the logged in user
     * @param language - language (language code) which is used for data presentation
     * @param usrPasswordChangeToken - password change token in case it is needed to change password
     * @param usrTermsAccepted - flag showing if user has accepted terms and conditions
     * @param defaultRoute - default navigation route for front-end application

     */
    public WebSessionInfo(String personName, String personLastName, String userName, String roleCode, Double roleId, String roleName, Double orgId, String orgName,
            String language, String usrPasswordChangeToken, String usrTermsAccepted, String defaultRoute) {
        super();
        this.personName = personName;
        this.personLastName = personLastName;
        this.userName = userName;
        this.roleCode = roleCode;
        this.roleId = roleId;
        this.roleName = roleName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.language = language;
        this.usrPasswordChangeToken = usrPasswordChangeToken;
        this.usrTermsAccepted = usrTermsAccepted;
        this.defaultRoute = defaultRoute;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRole(String roleCode) {
        this.roleCode = roleCode;
    }

    public Double getRoleId() {
        return roleId;
    }

    public void setRoleId(Double roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Double getOrgId() {
        return orgId;
    }

    public void setOrgId(Double orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUsrPasswordChangeToken() {
        return usrPasswordChangeToken;
    }

    public void setUsrPasswordChangeToken(String usrPasswordChangeToken) {
        this.usrPasswordChangeToken = usrPasswordChangeToken;
    }

    public String getUsrTermsAccepted() {
        return usrTermsAccepted;
    }

    public void setUsrTermsAccepted(String usrTermsAccepted) {
        this.usrTermsAccepted = usrTermsAccepted;
    }

    public String getDefaultRoute() {
        return defaultRoute;
    }

    public void setDefaultRoute(String defaultRoute) {
        this.defaultRoute = defaultRoute;
    }

    @Override
    public String toString() {
        return "WebSessionInfo [personName=" + personName + ", personLastName=" + personLastName + ", userName=" + userName + ", roleCode=" + roleCode
                + ", roleId=" + roleId + ", roleName=" + roleName + ", orgId=" + orgId + ", orgName=" + orgName + ", language=" + language
                + ", usrPasswordChangeToken=" + usrPasswordChangeToken + ", usrTermsAccepted=" + usrTermsAccepted + ", defaultRoute=" + defaultRoute + "]";
    }

}
