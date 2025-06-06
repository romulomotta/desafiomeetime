package com.lobato.desafiomeetime.config.token;

import com.lobato.desafiomeetime.application.TokenMapper;
import com.lobato.desafiomeetime.application.domain.TokenRequestDomain;
import com.lobato.desafiomeetime.application.domain.TokenResponseDomain;
import com.lobato.desafiomeetime.config.properties.HubSpotProperties;
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
public class TokenIntegration {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenIntegration.class);

    private final TokenClient client;
    private final HubSpotProperties properties;
    private final TokenMapper mapper;

    public TokenIntegration(TokenClient client,
                            HubSpotProperties properties,
                            TokenMapper mapper) {
        this.client = client;
        this.properties = properties;
        this.mapper = mapper;
    }

    public TokenResponseDomain getToken(TokenRequestDomain domain) {
        try {
            return mapper.toDomain(client.getAccessToken(createBody(domain)));
        } catch (FeignException.FeignClientException ex) {
            LOGGER.error("Erro 4xx, ao buscar Token Access. %s".formatted(ex.contentUTF8()));
            throw new RestClientException("Erro de cliente na API de Token", ex);
        } catch (FeignException.FeignServerException ex) {
            LOGGER.error("Erro 5xx, ao buscar o Token Access. %s".formatted(ex.contentUTF8()));
            throw new HttpServerErrorException(HttpStatus.valueOf(ex.status()), ex.contentUTF8());
        }
    }

    public MultiValueMap<String, String> createBody(TokenRequestDomain domain) {
        String nameParam = domain.isRefresh() ? "refresh_token" : "code";

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("grant_type", properties.getGrantType());
        request.add("client_id", properties.getClientId());
        request.add("client_secret", properties.getSecret());
        request.add("redirect_uri", properties.getRedirectUri());
        request.add(nameParam, domain.accessCode());

        return request;
    }
}
