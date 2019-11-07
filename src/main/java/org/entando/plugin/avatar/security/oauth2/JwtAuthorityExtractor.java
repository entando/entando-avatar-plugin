package org.entando.plugin.avatar.security.oauth2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.entando.plugin.avatar.config.EntandoProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class JwtAuthorityExtractor extends JwtAuthenticationConverter {

    private EntandoProperties entandoProperties;

    public JwtAuthorityExtractor(EntandoProperties entandoProperties) {
        this.entandoProperties = entandoProperties;
    }

    @Override
    protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        return this.extractAuthorityFromClaims(jwt.getClaims());
    }

    public List<GrantedAuthority> extractAuthorityFromClaims(Map<String, Object> claims) {
        return mapRolesToGrantedAuthorities(
            getRolesFromClaims(claims));
    }

    @SuppressWarnings("unchecked")
    private Collection<String> getRolesFromClaims(Map<String, Object> claims) {
        Map<String, Object> resourceAccessClaim = (Map<String, Object>) claims.getOrDefault("resource_access", new HashMap<>());
        if (resourceAccessClaim.containsKey(this.entandoProperties.getClientId())) {
            return (Collection<String>) ((Map)resourceAccessClaim.get(this.entandoProperties.getClientId())).getOrDefault("roles", new ArrayList<>());
        } else {
            return new ArrayList<>();
        }
    }

    private List<GrantedAuthority> mapRolesToGrantedAuthorities(Collection<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
