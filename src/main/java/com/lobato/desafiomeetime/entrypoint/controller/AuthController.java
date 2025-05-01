package com.lobato.desafiomeetime.entrypoint.controller;

import com.lobato.desafiomeetime.application.TokenMapper;
import com.lobato.desafiomeetime.application.service.AuthService;
import com.lobato.desafiomeetime.entrypoint.dto.TokenResponseDto;
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
    private final TokenMapper mapper;

    public AuthController(AuthService service, TokenMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<Void> getAuthUrl() {
        String result = service.getUrlAutenticacao();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(result));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("auth-callback")
    public ResponseEntity<TokenResponseDto> getToken(@RequestParam("code") String code) {
        TokenResponseDto result = service.getAccessToken(mapper.toRequestTokenDomain(code, false));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
