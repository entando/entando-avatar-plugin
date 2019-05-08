package org.entando.plugin.avatar.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "entando", ignoreUnknownFields = false)
public class EntandoProperties {

    private String clientId;
    private String clientSecret;
    private String accessTokenUri;
    private String authServiceUri;

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
}
