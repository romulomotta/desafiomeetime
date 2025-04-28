package com.lobato.desafiomeetime.repository.Integration;

import com.lobato.desafiomeetime.application.AuthMapper;
import com.lobato.desafiomeetime.application.domain.AccessTokenDomain;
import com.lobato.desafiomeetime.config.properties.HubSpotProperties;
import com.lobato.desafiomeetime.repository.client.AuthClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

@Service
public class AuthIntegration {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthIntegration.class);

    private final AuthClient client;
    private final HubSpotProperties properties;
    private final AuthMapper mapper;

    public AuthIntegration(AuthClient client,
                           HubSpotProperties properties,
                           AuthMapper mapper) {
        this.client = client;
        this.properties = properties;
        this.mapper = mapper;
    }

    public AccessTokenDomain getToken(String code) {
        try {
            return mapper.toDomain(client.getAccessToken(createBody(code)));
        } catch (FeignException.FeignClientException ex) {
            LOGGER.error("Erro 4xx, ao buscar Token Access. %s".formatted(ex.contentUTF8()));
            throw new RestClientException("Erro de cliente na API de Token", ex);
        } catch (FeignException.FeignServerException ex) {
            LOGGER.error("Erro 5xx, ao buscar o Token Access. %s".formatted(ex.contentUTF8()));
            throw new HttpServerErrorException(HttpStatus.valueOf(ex.status()), ex.contentUTF8());
        }
    }

    private MultiValueMap<String, String> createBody(String code) {

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("grant_type", properties.getGrantType());
        request.add("client_id", properties.getClientId());
        request.add("client_secret", properties.getSecret());
        request.add("redirect_uri", properties.getRedirectUri());
        request.add("code", code);

        return request;
    }
}
