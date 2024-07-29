package lt.project.jwt;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.jwt.util.JwtUtil;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;

public class PrototypeBackendJwtUtil extends JwtUtil {

    @Override
    protected BackendUserSession createUserSession() {
        return new SprBackendUserSession();
    }

}
