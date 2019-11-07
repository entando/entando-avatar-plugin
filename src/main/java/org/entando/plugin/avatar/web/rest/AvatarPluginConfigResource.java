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
package org.entando.plugin.avatar.web.rest;

import javax.validation.Valid;
import org.entando.plugin.avatar.config.AvatarPluginConfig;
import org.entando.plugin.avatar.config.AvatarPluginConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for managing Avatar Plugin Configuration.
 */
@RestController
@RequestMapping("/api")
public class AvatarPluginConfigResource {

    private final AvatarPluginConfigManager avatarPluginConfigManager;

    public AvatarPluginConfigResource(AvatarPluginConfigManager configManager) {
        this.avatarPluginConfigManager = configManager;
    }

    @GetMapping("/config")
    public ResponseEntity<AvatarPluginConfig> getConfig() {
        return ResponseEntity.ok(avatarPluginConfigManager.getAvatarPluginConfig());
    }

    @PreAuthorize("hasAuthority('config-update')")
    @PutMapping("/config")
    public ResponseEntity<AvatarPluginConfig> updateConfig(@Valid @RequestBody AvatarPluginConfig config) {
        avatarPluginConfigManager.update(config);
        return ResponseEntity.ok(config);
    }
}
