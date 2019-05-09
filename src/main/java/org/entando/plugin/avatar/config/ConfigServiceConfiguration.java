package org.entando.plugin.avatar.config;

import org.entando.config.ConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigServiceConfiguration {

    @Bean
    public ConfigService<AvatarConfig> configService(EntandoProperties entandoProperties) {
        return new ConfigService<>(entandoProperties.getClientId(), entandoProperties.getClientSecret(),
            entandoProperties.getAccessTokenUri(), entandoProperties.getConfigServiceUri(), AvatarConfig.class);
    }

}
