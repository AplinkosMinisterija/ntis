package eu.itreegroup.s2.server.rest.model;


public class ChangePasswordRequest {
    
    private String oldPassword;
    private String newPassword;
    
    public String getOldPassword() {
        return oldPassword;
    }
    
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    
    public String getNewPassword() {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "LbcResetPasswordRequest [oldPassword=" + oldPassword + ", newPassword=" + newPassword + "]";
    }

}
