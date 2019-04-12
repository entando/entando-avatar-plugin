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

import java.util.Arrays;
import java.util.List;

public class AvatarConfig {

    private AvatarStyle style;
    private int imageMaxSize;
    private int imageWidth;
    private int imageHeight;
    private List<String> imageTypes;
    private String gravatarUrl;

    public AvatarStyle getStyle() {
        return style;
    }

    public void setStyle(AvatarStyle style) {
        this.style = style;
    }

    /**
     * Image Max Size in MB.
     */
    public int getImageMaxSize() {
        return imageMaxSize;
    }

    public void setImageMaxSize(int imageMaxSize) {
        this.imageMaxSize = imageMaxSize;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    /**
     * Allowed image file extensions.
     */
    public List<String> getImageTypes() {
        return imageTypes;
    }

    public void setImageTypes(List<String> imageTypes) {
        this.imageTypes = imageTypes;
    }

    public String getGravatarUrl() {
        return gravatarUrl;
    }

    public void setGravatarUrl(String gravatarUrl) {
        this.gravatarUrl = gravatarUrl;
    }

    public static AvatarConfig getDefault() {
        AvatarConfig defaultAvatarConfig = new AvatarConfig();
        defaultAvatarConfig.style = AvatarStyle.DEFAULT;
        defaultAvatarConfig.imageMaxSize = 100;
        defaultAvatarConfig.imageWidth = 56;
        defaultAvatarConfig.imageHeight = 56;
        defaultAvatarConfig.imageTypes = Arrays.asList("png", "jpg");
        defaultAvatarConfig.gravatarUrl = "http://www.gravatar.com/avatar/";
        return defaultAvatarConfig;
    }
}
