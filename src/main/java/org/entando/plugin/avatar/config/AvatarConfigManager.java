/*
 * Copyright 2019-Present Entando Inc. (http://www.entando.com) All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package org.entando.plugin.avatar.config;


import org.entando.config.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AvatarConfigManager {

    private final Logger logger = LoggerFactory.getLogger(AvatarConfigManager.class);

    private final ConfigService<AvatarConfig> configService;

    @Autowired
    public AvatarConfigManager(final ConfigService<AvatarConfig> configService) {
        this.configService = configService;
    }

    public AvatarConfig getAvatarConfig() {
        AvatarConfig config = AvatarConfig.getDefault();
        try {
            config = configService.getConfig();
        } catch (Exception e) {
            logger.error("An error occurred while retrieving configuration from config service", e);
            configService.updateConfig(config);
        }
        return config;
    }

    public void updateAvatarConfig(AvatarConfig avatarConfig) {
        configService.updateConfig(avatarConfig);
    }

}
