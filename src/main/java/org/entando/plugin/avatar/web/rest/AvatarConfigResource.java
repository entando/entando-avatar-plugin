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
import org.entando.plugin.avatar.config.AvatarConfig;
import org.entando.plugin.avatar.config.AvatarConfigManager;
import org.entando.plugin.avatar.security.roles.AvatarConfigResourceRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.entando.plugin.avatar.security.roles.AvatarConfigResourceRoles.UPDATE_CONFIG;

/**
 * REST controller for managing Avatar Plugin Configuration.
 */
@RestController
@RequestMapping("/api")
public class AvatarConfigResource {

    private final AvatarConfigManager configManager;

    @Autowired
    public AvatarConfigResource(AvatarConfigManager configManager) {
        this.configManager = configManager;
    }

    @GetMapping("/config")
    public ResponseEntity<AvatarConfig> getConfig() {
        return ResponseEntity.ok(configManager.getAvatarConfig());
    }

    @Secured(UPDATE_CONFIG)
    @PutMapping("/config")
    public ResponseEntity<AvatarConfig> updateConfig(@Valid @RequestBody AvatarConfig config) {
        configManager.updateAvatarConfig(config);
        return ResponseEntity.ok(config);
    }
}
