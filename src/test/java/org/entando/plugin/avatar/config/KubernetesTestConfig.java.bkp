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
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class KubernetesTestConfig {

    @Bean
    @Primary
    public KubernetesClient kubernetesClient() {

        KubernetesClient client = mock(KubernetesClient.class);

        ConfigMap configMap = mock(ConfigMap.class);
        when(configMap.getData()).thenReturn(getDefaultConfig());

        Resource resource = mock(Resource.class);
        when(resource.get()).thenReturn(configMap);

        NonNamespaceOperation nonNamespaceOperation = mock(NonNamespaceOperation.class);
        when(nonNamespaceOperation.withName(any())).thenReturn(resource);

        MixedOperation mixedOperation = mock(MixedOperation.class);
        when(mixedOperation.inNamespace(any())).thenReturn(nonNamespaceOperation);

        when(client.configMaps()).thenReturn(mixedOperation);

        return client;
    }

    private Map<String, String> getDefaultConfig() {
        Map<String, String> map = new HashMap<>();
        map.put("avatar-style", AvatarStyle.LOCAL.toString());
        map.put("avatar-image-max-size", "100");
        map.put("avatar-image-width", "56");
        map.put("avatar-image-height", "56");
        map.put("avatar-image-types", "png,jpg");
        map.put("gravatar-url", "http://www.gravatar.com/avatar/");
        return map;
    }
}
