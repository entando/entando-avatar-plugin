package org.entando.plugin.avatar.config;

import org.entando.config.ConfigService;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigServiceConfiguration {

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.auth-server-url}")
    private String keycloakAuthServerUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${entando.config-service-uri}")
    private String configServiceUri;


    @Bean
    public ConfigService<AvatarConfig> configService() {
        return new ConfigService<>(clientId, clientSecret,
            keycloakAuthServerUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/token",
            configServiceUri, AvatarConfig.class);
    }

}
