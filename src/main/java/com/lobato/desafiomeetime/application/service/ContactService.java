package com.lobato.desafiomeetime.application.service;

import com.lobato.desafiomeetime.application.ContactMapper;
import com.lobato.desafiomeetime.application.domain.ContactRequestDomain;
import com.lobato.desafiomeetime.entrypoint.dto.ContactResponseDto;
import com.lobato.desafiomeetime.repository.Integration.ContactIntegration;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private final ContactIntegration integration;
    private final AuthService authService;
    private final ContactMapper mapper;

    public ContactService(ContactIntegration integration, AuthService authService, ContactMapper mapper) {
        this.integration = integration;
        this.authService = authService;
        this.mapper = mapper;
    }

    public ContactResponseDto createContact(ContactRequestDomain request) {

        return mapper.toDto(integration.createContact(request));
    }

    public void handleNotification() {
        // Lógica para salvar em um banco a informação ou entregar a outra aplicação
    }
}
