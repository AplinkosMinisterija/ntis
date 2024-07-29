package eu.itreegroup.spark.app.password;

import java.sql.Connection;
import java.util.HashMap;

public interface PasswordManagerService {

    /**
     * Function will check new password according to complexity requirements. Function is wrapper and will call function
     * checkPassword(String password, String currentPasswordHash, String passHistoryStr)
     * @param conn - connection to the db, used for data about user extraction
     * @param algorithmCode - code used for identify what algorithm should be used
     * @param password - new password
     * @param userId - reference to the user record, for which will be checked new password
     * @throws Exception
     */
    public void checkPassword(Connection conn, String algorithmCode, String password, Double userId) throws Exception;

    /**
     * Function will check password complexity.
     * @param algorithmCode - code used for identify what algorithm should be used
     * @param password - new password that should be checked
     * @param currentPasswordHash - current password hashed
     * @param passHistoryStr - previous passwords
     * @throws Exception
     */
    public void checkPassword(String algorithmCode, String password, String currentPasswordHash, String passHistoryStr) throws Exception;

    /**
     * Function will construct list of used passwords. newPassword will be added to the password history string. Password history string will contains
     * only last psw_history_length password. Property psw_history_length is defined in db
     * @param algorithmCode - code used for identify what algorithm should be used
     * @param passwordHistory - string that contains last psw_history_length passwords (hashed)
     * @param newPassword - new hashed password
     * @return - list of last psw_history_length delimited by ";"
     * @throws Exception
     */
    public String getPasswordHistory(String algorithmCode, String passwordHistory, String newPassword) throws Exception;

    /**
     * Function will return last psw_history_length passwords concatenated as string. psw_history_length is defined in spr_properties table.
     * @param conn - connection to database
     * @param algorithmCode - code used for identify what algorithm should be used
     * @param passwordHistory - used password list delimited by ";" newest password is first.
     * @return passwordHistory - that contains only last psw_history_length passwords delimited by ";"
     * @throws Exception
     * @deprecated Should be used {@code getPasswordHistory(String passwordHistory, String newPassword)}
     */
    @Deprecated
    public String getPasswordHistory(Connection conn, String algorithmCode, String passwordHistory) throws Exception;

    /**
     * Function will hash provided password.
     * @param algorithmCode - code used for identify what algorithm should be used
     * @param userPassword - user password that should be hashed
     * @param salt - string that can be used for provided password adjustment (salt)
     * @return hashed password
     */
    public String getPasswordHash(String algorithmCode, String userPassword, String salt);

    /**
     * Function will generate random password
     * @param psswordLength - password length that should be generated.
     * @return - generated password
     */
    public String generatePassword(int psswordLength);

    /**
     * Function will return days count how long can be used password. In case if this property not used function will return null;
     * @param algorithmCode - code used for identify what algorithm should be used
     * @return
     */
    public Double getPasswordValidInDays(String algorithmCode);

    /**
     * Function will return array of properties, that participates in new password validation.
     * @param algorithmCode - algorithm code that identify what algorithm for password validation should be used.
     * @return list of properties and his values.
     */
    public HashMap<String, String> getPasswordProperties(String algorithmCode);

}
