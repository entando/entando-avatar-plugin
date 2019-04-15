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

import io.fabric8.kubernetes.client.server.mock.KubernetesServer;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AvatarConfigManagerTest {

    @Rule
    public KubernetesServer server = new KubernetesServer(true, true);

    @Test
    public void testUpdateConfig() {

        AvatarConfigManager configManager = new AvatarConfigManager(server.getClient());

        AvatarConfig defaultConfig = AvatarConfig.getDefault();
        AvatarConfig avatarConfig = configManager.getAvatarConfig();
        assertThat(avatarConfig.getStyle()).isEqualTo(defaultConfig.getStyle());

        avatarConfig.setStyle(AvatarStyle.GRAVATAR);

        configManager.updateAvatarConfig(avatarConfig);
    }
}
