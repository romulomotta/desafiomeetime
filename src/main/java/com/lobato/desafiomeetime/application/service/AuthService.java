package com.lobato.desafiomeetime.application.service;

import com.lobato.desafiomeetime.application.AuthMapper;
import com.lobato.desafiomeetime.config.properties.HubSpotProperties;
import com.lobato.desafiomeetime.entrypoint.dto.AccessTokenDto;
import com.lobato.desafiomeetime.repository.Integration.AuthIntegration;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final String HUBSPOT_URL = "https://app.hubspot.com/oauth/authorize";
    private final HubSpotProperties properties;
    private final AuthIntegration integration;
    private final AuthMapper mapper;

    public AuthService(HubSpotProperties properties,
                       AuthIntegration integration,
                       AuthMapper mapper) {
        this.properties = properties;
        this.integration = integration;
        this.mapper = mapper;
    }

    public String getUrlAutenticacao() {
        return (HUBSPOT_URL.concat("?client_id=%s&redirect_uri=%s&scope=%s")
                .formatted(properties.getClientId(), properties.getRedirectUri(), properties.getScope()));
    }

    public AccessTokenDto getToken(String code) {
        return mapper.toDto(integration.getToken(code));
    }
}
