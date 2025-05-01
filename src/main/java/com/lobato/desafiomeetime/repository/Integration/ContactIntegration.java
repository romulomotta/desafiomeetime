package com.lobato.desafiomeetime.repository.Integration;

import com.lobato.desafiomeetime.application.ContactMapper;
import com.lobato.desafiomeetime.application.TokenMapper;
import com.lobato.desafiomeetime.application.domain.ContactRequestDomain;
import com.lobato.desafiomeetime.application.domain.TokenResponseDomain;
import com.lobato.desafiomeetime.config.token.TokenIntegration;
import com.lobato.desafiomeetime.repository.client.ContactClient;
import com.lobato.desafiomeetime.repository.entity.ContactResponseEntity;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import static com.lobato.desafiomeetime.config.utils.HeaderUtils.createHeaderWithBearer;

@Service
public class ContactIntegration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactIntegration.class);
    private final ContactMapper mapper;
    private final TokenMapper tokenMapper;
    private final ContactClient client;
    private final TokenIntegration tokenIntegration;

    public ContactIntegration(ContactMapper mapper, TokenMapper tokenMapper, ContactClient client, TokenIntegration tokenIntegration) {
        this.mapper = mapper;
        this.tokenMapper = tokenMapper;
        this.client = client;
        this.tokenIntegration = tokenIntegration;
    }

    @Retryable(include = {FeignException.TooManyRequests.class},
            maxAttempts = 2, backoff = @Backoff(delay = 11000))
    public ContactResponseEntity createContact(ContactRequestDomain domain) {
        try {
            return client.createContact(createHeaderWithBearer(domain.token()), mapper.toEntity(domain));
        } catch (FeignException.FeignClientException ex) {
            if (ex.status() == HttpStatus.UNAUTHORIZED.value()) {
                retryWithUpdatedToken(domain, updateToken(domain));
            }
            LOGGER.error("Erro 4xx, ao buscar Token Access. %s".formatted(ex.contentUTF8()));
            throw new RestClientException("Erro de cliente na API de Token", ex);
        } catch (FeignException.FeignServerException ex) {
            LOGGER.error("Erro 5xx, ao buscar o Token Access. %s".formatted(ex.contentUTF8()));
            throw new HttpServerErrorException(HttpStatus.valueOf(ex.status()), ex.contentUTF8());
        }
    }

    private void retryWithUpdatedToken(ContactRequestDomain requestDomain, TokenResponseDomain responseDomain) {
        ContactRequestDomain updatedRequest = new ContactRequestDomain(
                responseDomain.accessToken(), responseDomain.refreshToken(),
                requestDomain.email(), requestDomain.lastname(), requestDomain.firstname());
        createContact(updatedRequest);
    }

    private TokenResponseDomain updateToken(ContactRequestDomain domain) {
        return tokenIntegration.getToken(tokenMapper.toRequestToken(domain, true));
    }
}
