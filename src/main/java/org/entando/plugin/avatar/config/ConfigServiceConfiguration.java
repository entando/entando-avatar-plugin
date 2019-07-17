package org.entando.plugin.avatar.config;

import org.entando.config.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigServiceConfiguration {

    @Value("security.oauth.client.client-id")
    private String clientId;

    @Value("security.oauth.client.client-secret")
    private String clientSecret;

    @Value("security.oauth.client.access-token-uri")
    private String accessTokenUri;

    @Value("entando.config-service-uri")
    private String configServiceUri;


    @Bean
    public ConfigService<AvatarConfig> configService() {
        return new ConfigService<>(clientId, clientSecret, accessTokenUri, configServiceUri, AvatarConfig.class);
    }

}
