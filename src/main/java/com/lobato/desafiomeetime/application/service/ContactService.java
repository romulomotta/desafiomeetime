package com.lobato.desafiomeetime.application.service;

import com.lobato.desafiomeetime.application.ContactMapper;
import com.lobato.desafiomeetime.application.domain.ContactRequestDomain;
import com.lobato.desafiomeetime.entrypoint.dto.ContactNotificationRequestDto;
import com.lobato.desafiomeetime.entrypoint.dto.ContactResponseDto;
import com.lobato.desafiomeetime.repository.Integration.ContactIntegration;
import com.lobato.desafiomeetime.repository.entity.ContactResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);
    private final ContactIntegration integration;
    private final AuthService authService;
    private final ContactMapper mapper;

    public ContactService(ContactIntegration integration, AuthService authService, ContactMapper mapper) {
        this.integration = integration;
        this.authService = authService;
        this.mapper = mapper;
    }

    public ContactResponseDto createContact(ContactRequestDomain request) {

        ContactResponseEntity response = integration.createContact(request);
        LOGGER.info("Contato [%s] criado com sucesso.".formatted(response.properties().firstname()));

        return mapper.toDto(response);
    }

    public void handleNotification(List<ContactNotificationRequestDto> request) {
        // Lógica para salvar em um banco a informação ou entregar a outra aplicação
        LOGGER.info(request.toString());
    }
}
