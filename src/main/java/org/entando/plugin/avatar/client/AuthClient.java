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
package org.entando.plugin.avatar.client;

import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:auth-client.properties")
public class AuthClient {

    @Value("${client-id}")
    protected String clientId;

    @Value("${client-secret}")
    protected String clientSecret;

    @Value("${access-token-uri}")
    protected String accessTokenUri;

    @Value("${auth-service-uri}")
    protected String authServiceUri;

    interface UserDetail {

        @RequestLine("GET /users/{userId}")
        User get(@Param("userId") String userId);
    }

    public User getUserDetail(String userId) {

        // IMPORTANT: don't reuse this objects for multiple calls.
        OAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
        ClientCredentialsResourceDetails resourceDetails = getClientCredentialsResourceDetails();

        OAuth2FeignRequestInterceptor oauth2Interceptor = new OAuth2FeignRequestInterceptor(clientContext, resourceDetails);

        return Feign.builder()
                .requestInterceptor(oauth2Interceptor)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(UserDetail.class, authServiceUri)
                .get(userId);
    }

    private ClientCredentialsResourceDetails getClientCredentialsResourceDetails() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setAuthenticationScheme(AuthenticationScheme.header);
        resourceDetails.setClientId(clientId);
        resourceDetails.setClientSecret(clientSecret);
        resourceDetails.setAccessTokenUri(accessTokenUri);
        return resourceDetails;
    }
}
