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
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

public class AvatarConfigManagerTest {

    @Mock
    private ConfigService<AvatarConfig> configService;

    @Test
    public void testUpdateConfig() {
        MockitoAnnotations.initMocks(this);
        final AvatarConfigManager configManager = new AvatarConfigManager(configService);

        when(configService.getConfig()).thenThrow(new RuntimeException());

        final AvatarConfig defaultConfig = AvatarConfig.getDefault();
        final AvatarConfig avatarConfig = configManager.getAvatarConfig();
        assertThat(defaultConfig.getStyle()).isEqualTo(avatarConfig.getStyle());
        assertThat(defaultConfig.getGravatarUrl()).isEqualTo(avatarConfig.getGravatarUrl());
        assertThat(defaultConfig.getImageWidth()).isEqualTo(avatarConfig.getImageWidth());
        assertThat(defaultConfig.getImageHeight()).isEqualTo(avatarConfig.getImageHeight());

        final AvatarConfig config = new AvatarConfig();
        config.setGravatarUrl(defaultConfig.getGravatarUrl());
        config.setImageHeight(256);
        config.setImageWidth(256);
        config.setImageTypes(Arrays.asList("png", "jpg"));
        config.setStyle(AvatarStyle.GRAVATAR);

        configManager.updateAvatarConfig(config);
        verify(configService, times(1)).updateConfig(same(config));
    }
}
