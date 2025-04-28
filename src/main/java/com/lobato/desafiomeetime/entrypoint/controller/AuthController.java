package com.lobato.desafiomeetime.entrypoint.controller;

import com.lobato.desafiomeetime.application.service.AuthService;
import com.lobato.desafiomeetime.entrypoint.dto.AccessTokenDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("autenticar")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Void> getAuthUrl() {
        String result = service.getUrlAutenticacao();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(result));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("auth-callback")
    public ResponseEntity<AccessTokenDto> getToken(@RequestParam("code") String code) {
        AccessTokenDto result = service.getToken(code);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
