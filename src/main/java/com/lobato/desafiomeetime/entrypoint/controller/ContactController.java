package com.lobato.desafiomeetime.entrypoint.controller;

import com.lobato.desafiomeetime.application.ContactMapper;
import com.lobato.desafiomeetime.application.service.ContactService;
import com.lobato.desafiomeetime.entrypoint.dto.ContactNotificationRequestDto;
import com.lobato.desafiomeetime.entrypoint.dto.ContactRequestDto;
import com.lobato.desafiomeetime.entrypoint.dto.ContactResponseDto;
import com.lobato.desafiomeetime.entrypoint.dto.SavedContactResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("contatos")
public class ContactController {

    private final ContactService service;
    private final ContactMapper mapper;

    public ContactController(ContactService service, ContactMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("criar")
    public ResponseEntity<ContactResponseDto> createContacts(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                             @RequestHeader("refresh") String refreshToken,
                                                             @RequestBody ContactRequestDto request) {


        ContactResponseDto response = service.createContact(
                mapper.toRequestDomain(request, token, refreshToken));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("notificar-novo")
    public ResponseEntity<Void> notificationNewContact(@RequestHeader("X-HubSpot-Signature") String sign,
                                                       @RequestBody List<ContactNotificationRequestDto> request) {
        service.handleContactNotification(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/id-salvo")
    public ResponseEntity<List<SavedContactResponseDto>> buscarIdSalvosWebHook() {

        var result = service.fetchWebhooksSavedIds();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
