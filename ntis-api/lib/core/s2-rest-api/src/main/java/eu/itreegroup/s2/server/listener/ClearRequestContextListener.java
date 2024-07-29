package eu.itreegroup.s2.server.listener;

import org.slf4j.MDC;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ClearRequestContextListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent event) {

    }

    @Override
    public void requestDestroyed(ServletRequestEvent event) {
        removeSlf4jContextValues();
    }

    public static void removeSlf4jContextValues() {
        MDC.remove("user");
        MDC.remove("role");
        MDC.remove("session");
        MDC.remove("clientIp");
        MDC.remove("hostname");
    }
}
