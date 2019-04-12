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

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.DoneableConfigMap;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvatarConfigManager {

    private static final String CONFIG_MAP_NAME = "avatar-config";
    private static final String AVATAR_STYLE_KEY = "avatar-style";
    private static final String NAMESPACE = "default";

    private final KubernetesClient client;

    @Autowired
    public AvatarConfigManager(KubernetesClient client) {
        this.client = client;
        createConfigMapIfNecessary();
    }

    private void createConfigMapIfNecessary() {

        if (getConfigMap() == null) {

            client.configMaps()
                    .createNew()
                    .withNewMetadata()
                    .withName(CONFIG_MAP_NAME)
                    .withNamespace(NAMESPACE)
                    .endMetadata()
                    .addToData(AVATAR_STYLE_KEY, AvatarConfig.DEFAULT.toString())
                    .done();
        }
    }

    public AvatarConfig getAvatarConfig() {
        return AvatarConfig.valueOf(getConfigMap().getData().get(AVATAR_STYLE_KEY));
    }

    private ConfigMap getConfigMap() {
        return getConfigMapResource().get();
    }

    public void updateAvatarConfig(AvatarConfig avatarConfig) {
        getConfigMapResource()
                .edit()
                .addToData(AVATAR_STYLE_KEY, avatarConfig.toString())
                .done();
    }

    private Resource<ConfigMap, DoneableConfigMap> getConfigMapResource() {
        return client.configMaps().inNamespace(NAMESPACE).withName(CONFIG_MAP_NAME);
    }
}
