
package org.entando.plugin.avatar.config;

import java.util.Arrays;
import java.util.List;

import org.entando.plugin.avatar.domain.AvatarStyle;

public class AvatarPluginConfig {

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

    public static AvatarPluginConfig getDefault() {
        AvatarPluginConfig defaultAvatarPluginConfig = new AvatarPluginConfig();
        defaultAvatarPluginConfig.style = AvatarStyle.DEFAULT;
        defaultAvatarPluginConfig.imageMaxSize = 100;
        defaultAvatarPluginConfig.imageWidth = 56;
        defaultAvatarPluginConfig.imageHeight = 56;
        defaultAvatarPluginConfig.imageTypes = Arrays.asList("png", "jpg");
        defaultAvatarPluginConfig.gravatarUrl = "http://www.gravatar.com/avatar/";
        return defaultAvatarPluginConfig;
    }
}
