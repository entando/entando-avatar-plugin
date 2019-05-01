package org.entando.plugin.avatar.config;

import org.entando.config.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:auth-client.properties")
public class ConfigServiceConfiguration {

    @Bean
    public ConfigService<AvatarConfig> configService(
            @Value("${client-id}") final String clientId,
            @Value("${client-secret}") final String clientSecret,
            @Value("${access-token-uri}") final String accessTokenUri,
            @Value("${config-service-uri}") final String configServiceUri) {
        return new ConfigService<>(clientId, clientSecret, accessTokenUri, configServiceUri, AvatarConfig.class);
    }

}
