package org.entando.plugin.avatar.config;

import org.entando.plugin.avatar.security.*;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import java.util.Collection;
import java.util.Map;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends ResourceServerConfigurerAdapter {

    private final SecurityProblemSupport problemSupport;

    private final ResourceServerProperties resourceServerProperties;

    public SecurityConfiguration(SecurityProblemSupport problemSupport, ResourceServerProperties resourceServerProperties) {
        this.problemSupport = problemSupport;
        this.resourceServerProperties = resourceServerProperties;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            .exceptionHandling()
            .authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport)
        .and()
            .headers()
            .frameOptions()
            .disable()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN);

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId(resourceServerProperties.getClientId());
        tokenServices.setClientSecret(resourceServerProperties.getClientSecret());
        tokenServices.setCheckTokenEndpointUrl(resourceServerProperties.getTokenInfoUri());
        tokenServices.setAccessTokenConverter(new KeycloakAccessTokenConverter());

        resources.resourceId(resourceServerProperties.getResourceId());
        resources.tokenServices(tokenServices);
    }

    public static class KeycloakAccessTokenConverter extends DefaultAccessTokenConverter {

        @Override
        public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
            OAuth2Authentication oAuth2Authentication = super.extractAuthentication(map);
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) oAuth2Authentication.getOAuth2Request().getAuthorities();
            if (map.containsKey("resource_access")) {
                Map<String, Object> resource_access = (Map<String, Object>) map.get("resource_access");
                if(resource_access.containsKey("avatar-plugin")) {
                    Map<String, Object> avatarPluginResource = (Map<String, Object>) resource_access.get("avatar-plugin");
                    if (avatarPluginResource.containsKey("roles")) {
                        ((Collection<String>) avatarPluginResource.get("roles")).forEach(r -> authorities.add(new SimpleGrantedAuthority(r)));
                    }
                }
            }
            return new OAuth2Authentication(oAuth2Authentication.getOAuth2Request(),oAuth2Authentication.getUserAuthentication());
        }

    }

    /**
     * This OAuth2RestTemplate is only used by AuthorizationHeaderUtil that is currently used by TokenRelayRequestInterceptor
     */
    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails,
        OAuth2ClientContext oAuth2ClientContext) {
        return new OAuth2RestTemplate(oAuth2ProtectedResourceDetails, oAuth2ClientContext);
    }

}
