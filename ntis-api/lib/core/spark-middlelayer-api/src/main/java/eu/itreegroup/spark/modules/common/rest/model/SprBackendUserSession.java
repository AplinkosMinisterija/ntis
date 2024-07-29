package eu.itreegroup.spark.modules.common.rest.model;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.spark.modules.admin.dao.SprSessionOpenDAO;

public class SprBackendUserSession extends SprSessionOpenDAO implements BackendUserSession {

    private static final long serialVersionUID = 1L;

    private String userPasswordChangeToken;

    private String usr_terms_accepted;

    private Date usr_terms_accepted_date;

    private Date usr_valid_from;

    private Date usr_valid_to;

    private String person_name;

    private String person_surname;

    private String role_name;

    private String role_type;

    private String language;

    private String defaultRoute;

    private String org_name;

    private String sessionType;

    private Double apiId;

    public SprBackendUserSession() {
        super();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + getRole());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Double getUsrId() {
        return this.getSes_usr_id();
    }

    @Override
    public Double getSesId() {
        return this.getSes_id();
    }

    @Override
    public String getSesKey() {
        return this.getSes_key();
    }

    @Override
    public void setSesId(Double parseDouble) {
        this.setSes_id(parseDouble);
    }

    @Override
    public void setSesKey(String string) {
        this.setSes_key(string);
    }

    @Override
    public void setUsrId(Double parseDouble) {
        this.setSes_usr_id(parseDouble);
    }

    @Override
    public boolean isAuthenticated() {
        return this.getSes_key() != null;
    }

    @Override
    public String getUserPasswordChangeToken() {
        return this.userPasswordChangeToken;
    }

    @Override
    public void setUserPasswordChangeToken(String usrPasswordChangeToken) {
        this.userPasswordChangeToken = usrPasswordChangeToken;
    }

    @Override
    public String getUsername() {
        return this.getSes_username();
    }

    @Override
    public String getBrowser() {
        return this.getSes_browser();
    }

    @Override
    public String getIp() {
        return this.getSes_ip();
    }

    @Override
    public String getRole() {
        return this.getSes_role();
    }

    @Override
    public void setBrowser(String arg0) {
        this.setSes_browser(arg0);
    }

    @Override
    public void setDaoSession(Object arg0) {

    }

    @Override
    public void setIp(String arg0) {
        this.setSes_ip(arg0);
    }

    @Override
    public void setRole(String arg0) {
        this.setSes_role(arg0);
    }

    @Override
    public void setUsername(String arg0) {
        this.setSes_username(arg0);
    }

    public Double getUsr_id() {
        return this.getSes_usr_id();
    }

    public void setUsr_id(Double usr_id) {
        this.setSes_usr_id(usr_id);
    }

    public Double getPer_id() {
        return this.getSes_per_id();
    }

    public void setPer_id(Double per_id) {
        this.setSes_per_id(per_id);
    }

    public String getUsr_terms_accepted() {
        return usr_terms_accepted;
    }

    public void setUsr_terms_accepted(String usr_terms_accepted) {
        this.usr_terms_accepted = usr_terms_accepted;
    }

    public Date getUsr_terms_accepted_date() {
        return usr_terms_accepted_date;
    }

    public void setUsr_terms_accepted_date(Date usr_terms_accepted_date) {
        this.usr_terms_accepted_date = usr_terms_accepted_date;
    }

    public Date getUsr_valid_from() {
        return usr_valid_from;
    }

    public void setUsr_valid_from(Date usr_valid_from) {
        this.usr_valid_from = usr_valid_from;
    }

    public Date getUsr_valid_to() {
        return usr_valid_to;
    }

    public void setUsr_valid_to(Date usr_valid_to) {
        this.usr_valid_to = usr_valid_to;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPerson_surname() {
        return person_surname;
    }

    public void setPerson_surname(String person_surname) {
        this.person_surname = person_surname;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRole_type() {
        return role_type;
    }

    public void setRole_type(String role_type) {
        this.role_type = role_type;
    }

    public void setDefaultRoute(String defaultRoute) {
        this.defaultRoute = defaultRoute;
    }

    public String getDefaultRoute() {
        return this.defaultRoute;
    }

    @Override
    public String toString() {
        return "BackendUserSession: User name: " + getSes_username() + " role id: " + getSes_rol_id() + " user id: " + getSes_usr_id();
    }

    public void setOrgName(String orgName) {
        this.org_name = orgName;
    }

    public String getOrg_name() {
        return this.org_name;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    @Override
    public Double getApiId() {
        return this.apiId;
    }

    @Override
    public void setApiId(Double apiId) {
        this.apiId = apiId;
    }

}
