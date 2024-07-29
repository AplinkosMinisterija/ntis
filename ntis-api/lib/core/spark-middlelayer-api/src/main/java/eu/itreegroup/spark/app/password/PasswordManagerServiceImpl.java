package eu.itreegroup.spark.app.password;

import java.sql.Connection;
import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.IdKeyValuePair;
import eu.itreegroup.spark.modules.admin.service.SprPropertiesDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;

public class PasswordManagerServiceImpl implements PasswordManagerService {

    @Autowired
    SprPropertiesDBService sprPropertiesDBService;

    @Autowired
    SprUsersDBService sprUsersDBService;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Override
    public String getPasswordHash(String algorithmCode, String userPassword, String salt) {
        return DigestUtils.sha256Hex(userPassword);
    }

    @Override
    public String generatePassword(int psswordLength) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
        return RandomStringUtils.random(psswordLength, characters);
    }

    @Override
    @Deprecated
    public String getPasswordHistory(Connection conn, String algorithmCode, String passwordHistory) throws Exception {
        String result = "";
        for (IdKeyValuePair prop : sprPropertiesDBService.loadPublicProperties(conn)) {
            if (constructPropertyName(algorithmCode, "psw_history_length").equals(prop.getId())) {
                String[] passHistory = passwordHistory.split(";");
                for (int i = 0; i < passHistory.length && i < Utils.getDouble(prop.getValue()); i++) {
                    result = result + passHistory[i] + ";";
                }
            }
        }
        return result;
    }

    @Override
    public String getPasswordHistory(String algorithmCode, String passwordHistory, String newPassword) throws Exception {
        String result = "";
        String param = dbPropertyManager.getPropertyByName(constructPropertyName(algorithmCode, "psw_history_length"), "-1");
        Double paramValue = Utils.getDouble(param);
        if (paramValue > 0) {
            passwordHistory = newPassword + ";" + passwordHistory;
            String[] passHistory = passwordHistory.split(";");
            for (int i = 0; i < passHistory.length && i < paramValue; i++) {
                result = result + passHistory[i] + ";";
            }
        }
        return result;
    }

    @Override
    public void checkPassword(Connection conn, String algorithmCode, String password, Double userId) throws Exception {
        String param = dbPropertyManager.getPropertyByName(constructPropertyName(algorithmCode, "psw_history_length"), "-1");
        Double paramValue = Utils.getDouble(param);
        String currentPasswordHash = null;
        String passHistoryStr = null;
        if (paramValue > 0) {
            SprUsersDAO usrDao = sprUsersDBService.loadRecord(conn, userId);
            currentPasswordHash = usrDao.getUsr_password_hash();
            passHistoryStr = usrDao.getUsr_password_history();
        }
        checkPassword(algorithmCode, password, currentPasswordHash, passHistoryStr);
    }

    @Override
    public void checkPassword(String algorithmCode, String password, String currentPasswordHash, String passHistoryStr) throws Exception {
        String passwordHash = getPasswordHash(algorithmCode, password, null);
        String param = dbPropertyManager.getPropertyByName(constructPropertyName(algorithmCode, "psw_min_capital"), "-1");
        Double paramValue = Utils.getDouble(param);
        if (paramValue > -1) {
            if (paramValue > password.chars().filter((s) -> Character.isUpperCase(s)).count()) {
                throw new SparkBusinessException(new S2Message("", "", "common.message.minCapital", SparkMessageType.ERROR, param));
            }
        }
        param = dbPropertyManager.getPropertyByName(constructPropertyName(algorithmCode, "psw_min_digits"), "-1");
        paramValue = Utils.getDouble(param);
        if (paramValue > -1) {
            if (paramValue > password.chars().filter((s) -> Character.isDigit(s)).count()) {
                throw new SparkBusinessException(new S2Message("password", "password", "common.message.minDigit", SparkMessageType.ERROR, param));
            }
        }
        param = dbPropertyManager.getPropertyByName(constructPropertyName(algorithmCode, "psw_history_length"), "-1");
        paramValue = Utils.getDouble(param);
        if (paramValue > 0) {
            if (currentPasswordHash != null && currentPasswordHash.equals(passwordHash)) {
                throw new SparkBusinessException(new S2Message("", "", "common.message.historyLength", SparkMessageType.ERROR, param));
            }
            if (paramValue > 1 && passHistoryStr != null && !passHistoryStr.isEmpty()) {
                String[] passHistory = passHistoryStr.split(";");
                for (int i = 0; i < passHistory.length && i < (paramValue - 1); i++) {
                    if (passHistory[i].equals(passwordHash)) {
                        throw new SparkBusinessException(new S2Message("", "", "common.message.historyLength", SparkMessageType.ERROR, param));
                    }
                }
            }
        }
    }

    @Override
    public Double getPasswordValidInDays(String algorithmCode) {
        return Utils.getDouble(dbPropertyManager.getPropertyByName(constructPropertyName(algorithmCode, "psw_valid_in_days"), null));
    }

    @Override
    public HashMap<String, String> getPasswordProperties(String algorithmCode) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("psw_min_length", dbPropertyManager.getPropertyByName(constructPropertyName(algorithmCode, "psw_min_length"), ""));
        data.put("psw_min_capital", dbPropertyManager.getPropertyByName(constructPropertyName(algorithmCode, "psw_min_capital"), ""));
        data.put("psw_min_digits", dbPropertyManager.getPropertyByName(constructPropertyName(algorithmCode, "psw_min_digits"), ""));
        data.put("psw_history_length", dbPropertyManager.getPropertyByName(constructPropertyName(algorithmCode, "psw_history_length"), ""));
        return data;
    }

    /**
     * Function will construct property name. If algorithmCode is not null then it will be added to the propertyName as prefix, otherwise property
     * name will be returned as is.
     * @param algorithmCode - string that identified algorithm and in case if not null will be concatenated with provided property name
     * @param propertyName - property name that should be modified according to provided algorithm code.
     * @return - modified property name.
     */
    private String constructPropertyName(String algorithmCode, String propertyName) {
        return (algorithmCode == null || algorithmCode.isBlank()) ? propertyName : algorithmCode + "_" + propertyName;
    }

}
