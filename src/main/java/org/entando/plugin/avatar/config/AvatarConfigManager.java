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
import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvatarConfigManager {

    private static final String CONFIG_MAP_NAME = "avatar-config";
    private static final String NAMESPACE = "default";

    private static final String AVATAR_STYLE_KEY = "avatar-style";
    private static final String AVATAR_IMAGE_MAX_SIZE = "avatar-image-max-size";
    private static final String AVATAR_IMAGE_WIDTH = "avatar-image-width";
    private static final String AVATAR_IMAGE_HEIGHT = "avatar-image-height";
    private static final String AVATAR_IMAGE_TYPES = "avatar-image-types";
    private static final String GRAVATAR_URL = "gravatar-url";

    private final KubernetesClient client;

    @Autowired
    public AvatarConfigManager(KubernetesClient client) {
        this.client = client;
        createConfigMapIfNecessary();
    }

    private void createConfigMapIfNecessary() {

        if (getConfigMap() == null) {

            AvatarConfig defaultConfig = AvatarConfig.getDefault();

            fillConfigMap(client.configMaps()
                    .createNew()
                    .withNewMetadata()
                    .withName(CONFIG_MAP_NAME)
                    .withNamespace(NAMESPACE)
                    .endMetadata(), defaultConfig)
                    .done();
        }
    }

    public AvatarConfig getAvatarConfig() {
        return getAvatarConfigFromConfigMap(getConfigMap());
    }

    private ConfigMap getConfigMap() {
        return getConfigMapResource().get();
    }

    public void updateAvatarConfig(AvatarConfig avatarConfig) {
        fillConfigMap(getConfigMapResource().edit(), avatarConfig).done();
    }

    private Resource<ConfigMap, DoneableConfigMap> getConfigMapResource() {
        return client.configMaps().inNamespace(NAMESPACE).withName(CONFIG_MAP_NAME);
    }

    private DoneableConfigMap fillConfigMap(DoneableConfigMap configMap, AvatarConfig avatarConfig) {
        return configMap.addToData(AVATAR_STYLE_KEY, avatarConfig.getStyle().toString())
                .addToData(AVATAR_IMAGE_MAX_SIZE, String.valueOf(avatarConfig.getImageMaxSize()))
                .addToData(AVATAR_IMAGE_WIDTH, String.valueOf(avatarConfig.getImageWidth()))
                .addToData(AVATAR_IMAGE_HEIGHT, String.valueOf(avatarConfig.getImageHeight()))
                .addToData(AVATAR_IMAGE_TYPES, String.join(",", avatarConfig.getImageTypes()))
                .addToData(GRAVATAR_URL, avatarConfig.getGravatarUrl());
    }

    private AvatarConfig getAvatarConfigFromConfigMap(ConfigMap configMap) {
        Map<String, String> properties = configMap.getData();
        AvatarConfig avatarConfig = new AvatarConfig();
        avatarConfig.setStyle(AvatarStyle.valueOf(properties.get(AVATAR_STYLE_KEY)));
        avatarConfig.setImageMaxSize(Integer.parseInt(properties.get(AVATAR_IMAGE_MAX_SIZE)));
        avatarConfig.setImageWidth(Integer.parseInt(properties.get(AVATAR_IMAGE_WIDTH)));
        avatarConfig.setImageHeight(Integer.parseInt(properties.get(AVATAR_IMAGE_HEIGHT)));
        avatarConfig.setImageTypes(Arrays.asList(properties.get(AVATAR_IMAGE_TYPES).split(",")));
        avatarConfig.setGravatarUrl(properties.get(GRAVATAR_URL));
        return avatarConfig;
    }
}
