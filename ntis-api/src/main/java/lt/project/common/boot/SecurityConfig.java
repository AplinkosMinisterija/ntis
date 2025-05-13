package lt.project.common.boot;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import eu.itreegroup.s2.server.jwt.JwtAuthenticationFailureHandler;
import eu.itreegroup.s2.server.jwt.JwtAuthenticationProvider;
import eu.itreegroup.s2.server.jwt.JwtAuthenticationSuccessHandler;
import eu.itreegroup.s2.server.jwt.S2JwtAuthenticationFilter;
import lt.project.jwt.PrototypeBackendJwtUtil;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).csrf((csrf) -> csrf.disable());
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().requestMatchers(getPublicRoutes());
    }

    protected String[] getPublicRoutes() {
        String[] routes = { //
                "/auth/login", //
                "/auth/admin-login", //
                "/auth/google-login", //
                "/auth/request-new-password", //
                "/auth/reset-password", //
                "/auth/reset-new-user-password", //
                "/ntis-common/check-if-new-user-link-valid", //
                "/ntis-common/check-if-change-password-valid", //
                "/auth/confirm-email", //
                "/auth/register", //
                "/auth/get-password-params", //
                "/i18n/*", //
                "/common/appData", //
                "/common/ref-codes/**", //
                "/public/**", //
                "/spr-stomp-endpoint", //
                "/spr-stomp-endpoint/**", //
                "/auth/viisp/init-mock-viisp-auth", //
                "/auth/viisp/init-test-viisp-auth", //
                "/auth/viisp/init-viisp-auth", //
                "/auth/viisp/finalize-viisp-auth", //
                "/auth/viisp/finalize-test-viisp-auth", //
                "/auth/viisp/finalize-mock-viisp-auth", //
                "/auth/viisp/create-session-viisp", //
                "/swagger-ui.html/**", //
                "/swagger-ui/**", //
                "/v3/api-docs/**", //

                "/ntis-map-pub/**", //
                "/ntis-contracts/search-address/*", //
                "/ntis-contracts/service-search", //
                "/ntis-contracts/contract-callback", //

                "/ntis-news/public/get-news-list", //
                "/ntis-news/public/view-nw/*", //
                "/ntis-news/get-page-template", //
                "/ntis-news/public/get-file/*", //

                "/ntis-faq-pub/**", //

                "/auth/isense/start-isense-auth", //
                "/auth/isense/complete-isense-auth", //
                "/ntis-common/works-info"//
        };
        return routes;
    }

    protected AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList((AuthenticationProvider) jwtAuthenticationProvider()));
    }

    @Bean
    S2JwtAuthenticationFilter jwtAuthenticationFilter() {
        S2JwtAuthenticationFilter s2JwtAuthenticationFilter = new S2JwtAuthenticationFilter();
        s2JwtAuthenticationFilter.setAuthenticationManager(authenticationManager());
        s2JwtAuthenticationFilter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler());
        s2JwtAuthenticationFilter.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler());
        return s2JwtAuthenticationFilter;
    }

    @Bean
    public FilterRegistrationBean<S2JwtAuthenticationFilter> registration(S2JwtAuthenticationFilter filter) {
        FilterRegistrationBean<S2JwtAuthenticationFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler() {
        return new JwtAuthenticationSuccessHandler();
    }

    @Bean
    public JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler() {
        return new JwtAuthenticationFailureHandler();
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider();
    }

    @Bean
    public PrototypeBackendJwtUtil jwtUtil() {
        return new PrototypeBackendJwtUtil();
    }

}
