package eu.itreegroup.spark.app;

import java.sql.Connection;
import java.util.Map;

import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;

// 2FA taškas framework'e; įgyvendinimas projekte (NtisTwoFactorHandler).
// SprAuthorization jį ima per @Autowired(required=false) — be bean'o elgesys nepakitęs (upstream-safe).
public interface TwoFactorHandler {

    boolean isRequired(Connection conn, SprUsersDAO userDAO, Map<String, Object> authExtData) throws Exception;

    // Grąžina slaptą handle, susietą su iššūkiu; klientas jį pateikia verify/resend metu (susieja 2 žingsnį su 1-uoju).
    String issueAndSend(Connection conn, SprUsersDAO userDAO) throws Exception;

    String maskEmail(String email);
}
