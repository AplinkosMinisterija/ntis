package lt.project.tx;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import eu.itreegroup.s2.client.exception.SparkRuntimeException;
import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.tx.S2TransactionManager;
import eu.itreegroup.spark.app.SprAuthorization;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;

public class PrototypeBackendTransactionManager extends S2TransactionManager<BackendUserSession> {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(PrototypeBackendTransactionManager.class);

    private ArrayList<String> urlsNotUsedForPasswordExpiredLogic;

    private String checkSessionURL;

    @Autowired
    SprAuthorization<SprBackendUserSession> sprAuthorization;

    public PrototypeBackendTransactionManager() {
        urlsNotUsedForPasswordExpiredLogic = new ArrayList<String>();
    }

    @Override
    protected BackendUserSession initDBSession(Connection conn) throws Exception {
        log.debug("init user session");
        BackendUserSession userSession = requestContext.getUserSession();
        if (userSession != null && (userSession.getApiId() != null || userSession.getSesKey() != null)) {
            String pathInfo = requestContext.getRequest().getPathInfo();
            if (pathInfo.endsWith("/")) {
                pathInfo = pathInfo.substring(0, pathInfo.length() - 1);
            }
            try {
                if (userSession.getApiId() != null) {
                    userSession = sprAuthorization.initApiSessionFromDB(conn, (SprBackendUserSession) userSession);
                } else {
                    userSession = sprAuthorization.initSessionFromDB(conn, (SprBackendUserSession) userSession);
                }

                requestContext.setUserSession(userSession);
                conn.commit();
            } catch (Exception ex) {
                log.error(ex.getMessage());
                userSession.setSesKey(null);
                userSession.setUsername(null);
                requestContext.setUserSession(userSession);
                if (pathInfo.equalsIgnoreCase(checkSessionURL)) {
                    S2Message message = new S2Message(null, // String module,
                            "USER_SESSION", // String item,
                            S2Message.MESSAGE_SOURCE_SERVER, // String source,
                            SprAuthorization.SESSION_EXPIRED, // String key,
                            SparkMessageType.ERROR, // SparkMessageType type,
                            "Session Expired.", // String text
                            (String[]) null);
                    throw new SparkRuntimeException(message);
                } else {
                    throw new SparkBusinessException(new S2Message("pages.adminLogin.userNotActivated", SparkMessageType.ERROR, "User is not activated."));
                }
            }
            if (!urlsNotUsedForPasswordExpiredLogic.contains(pathInfo) && requestContext.getUserSession() != null) {
                SprBackendUserSession session = (SprBackendUserSession) requestContext.getUserSession();
                if (session.getSes_password_reset_req_date() != null) {
                    Date currentDate = new Date();
                    if (currentDate.after(session.getSes_password_reset_req_date())) {
                        S2Message message = new S2Message(null, // String module,
                                "USER_PASSWORD", // String item,
                                S2Message.MESSAGE_SOURCE_SERVER, // String source,
                                SprAuthorization.PASSOWORD_EXPIRED, // String key,
                                SparkMessageType.ERROR, // SparkMessageType type,
                                "Password Expired.", // String text
                                (String[]) null);
                        throw new SparkRuntimeException(message);
                    }
                }
            }
        } else

        {
            log.debug("No session info found will be created anonymous session");
            userSession = new SprBackendUserSession();
            ((SprBackendUserSession) userSession).setSes_usr_id(Double.valueOf(SprAuthorization.ANONYMOUS_USER_ID));
            ((SprBackendUserSession) userSession).setSes_username("ANONYMOUS");
            ((SprBackendUserSession) userSession).setSes_rol_id(sprAuthorization.getPublicRoleId(conn));
            requestContext.setUserSession(userSession);
        }
        return userSession;
    }

    @Override
    protected void clearDBSession(Connection conn) throws Exception {
        log.debug("clear user session");
        /*	
        	PLSQLExecutor exec = new PLSQLExecutor(conn);
        	SprAuthorization sprAuthorization = new SprAuthorization();
        	sprAuthorization.clearDBUserSessionState(conn);
        	conn.commit();
        */
    }

    public void setUrlsNotUsedForPasswordExpiredLogic(ArrayList<String> urlsNotUsedForPasswordExpiredLogic) {
        this.urlsNotUsedForPasswordExpiredLogic = urlsNotUsedForPasswordExpiredLogic;
    }

    public ArrayList<String> getUrlsNotUsedForPasswordExpiredLogic() {
        return this.urlsNotUsedForPasswordExpiredLogic;
    }

    public String getCheckSessionURL() {
        return checkSessionURL;
    }

    public void setCheckSessionURL(String checkSessionURL) {
        this.checkSessionURL = checkSessionURL;
    }

}
