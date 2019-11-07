
package org.entando.plugin.avatar.config;

import org.entando.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AvatarPluginConfigManager {

    private final ConfigService<AvatarPluginConfig> configService;

    @Autowired
    public AvatarPluginConfigManager(final ConfigService<AvatarPluginConfig> configService) {
        this.configService = configService;
    }

    public AvatarPluginConfig getAvatarPluginConfig() {
        return Optional.ofNullable(configService.getConfig())
            .orElseGet(AvatarPluginConfig::getDefault);
    }

    public void update(AvatarPluginConfig avatarPluginConfig) {
        configService.updateConfig(avatarPluginConfig);
    }

}

