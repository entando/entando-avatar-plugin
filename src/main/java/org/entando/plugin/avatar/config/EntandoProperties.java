
package org.entando.plugin.avatar.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Entando Properties
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "entando", ignoreUnknownFields = false)
public class EntandoProperties {

    private String clientId;
    private String clientSecret;
    private String accessTokenUri;
    private String authServiceUri;
    private String configServiceUri;
    private String widgetsFolder;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAccessTokenUri() {
        return accessTokenUri;
    }

    public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }

    public String getAuthServiceUri() {
        return authServiceUri;
    }

    public void setAuthServiceUri(String authServiceUri) {
        this.authServiceUri = authServiceUri;
    }

    public String getConfigServiceUri() {
        return configServiceUri;
    }

    public void setConfigServiceUri(String configServiceUri) {
        this.configServiceUri = configServiceUri;
    }

    public String getWidgetsFolder() {
        return widgetsFolder;
    }

    public EntandoProperties setWidgetsFolder(String widgetsFolder) {
        this.widgetsFolder = widgetsFolder;
        return this;
    }
}
