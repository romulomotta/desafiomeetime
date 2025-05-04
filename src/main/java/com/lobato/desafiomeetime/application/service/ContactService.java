package com.lobato.desafiomeetime.application.service;

import com.lobato.desafiomeetime.application.ContactMapper;
import com.lobato.desafiomeetime.application.domain.ContactRequestDomain;
import com.lobato.desafiomeetime.entrypoint.dto.ContactNotificationRequestDto;
import com.lobato.desafiomeetime.entrypoint.dto.ContactResponseDto;
import com.lobato.desafiomeetime.entrypoint.dto.SavedContactResponseDto;
import com.lobato.desafiomeetime.repository.external.Integration.ContactIntegration;
import com.lobato.desafiomeetime.repository.external.entity.ContactResponseEntity;
import com.lobato.desafiomeetime.repository.internal.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ContactService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);
    private final ContactIntegration integration;
    private final ContactMapper mapper;
    private final ContactRepository repository;

    public ContactService(ContactIntegration integration,
                          AuthService authService,
                          ContactMapper mapper,
                          ContactRepository repository) {
        this.integration = integration;
        this.mapper = mapper;
        this.repository = repository;
    }

    public ContactResponseDto createContact(ContactRequestDomain request) {

        ContactResponseEntity response = integration.createContact(request);
        LOGGER.info("Contato [%s] criado com sucesso.".formatted(response.properties().firstname()));

        return mapper.toDto(response);
    }

    public void handleContactNotification(List<ContactNotificationRequestDto> request) {
        LOGGER.info(request.toString());

        request.stream()
                .filter(Objects::nonNull)
                .forEach(item -> {
                    try {
                        repository.save(mapper.toDataEntity(item.objectId()));
                        LOGGER.info(("Salvo..." + item.objectId()));
                    } catch (Exception e) {
                        LOGGER.error("Falha ao salvar no banco de dados: " + item.objectId());
                        throw new RuntimeException("Falha ao salvar no banco de dados: " + item.objectId());
                    }
                });
    }

    public List<SavedContactResponseDto> fetchWebhooksSavedIds() {
        List<SavedContactResponseDto> webhookSavedContacts = new ArrayList<>();

        var result = repository.findAll();
        result.forEach(contact -> {
            webhookSavedContacts.add(mapper.toWebhookContactDto(contact));
        });

        return webhookSavedContacts;
    }
}
