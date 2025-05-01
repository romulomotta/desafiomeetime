package com.lobato.desafiomeetime.repository.Integration;

import com.lobato.desafiomeetime.application.ContactMapper;
import com.lobato.desafiomeetime.application.domain.ContactRequestDomain;
import com.lobato.desafiomeetime.repository.client.ContactClient;
import com.lobato.desafiomeetime.repository.entity.ContactResponseEntity;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

@Service
public class ContactIntegration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactIntegration.class);
    private final ContactMapper mapper;
    private final ContactClient client;

    public ContactIntegration(ContactMapper mapper, ContactClient client) {
        this.mapper = mapper;
        this.client = client;
    }

    public ContactResponseEntity createContact(ContactRequestDomain domain) {
        try {
            return client.createContact(createHeaders(domain.token()), mapper.toEntity(domain));
        } catch (FeignException.FeignClientException ex) {
            LOGGER.error("Erro 4xx, ao buscar Token Access. %s".formatted(ex.contentUTF8()));
            throw new RestClientException("Erro de cliente na API de Token", ex);
        } catch (FeignException.FeignServerException ex) {
            LOGGER.error("Erro 5xx, ao buscar o Token Access. %s".formatted(ex.contentUTF8()));
            throw new HttpServerErrorException(HttpStatus.valueOf(ex.status()), ex.contentUTF8());
        }
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token));

        return headers;
    }
}
