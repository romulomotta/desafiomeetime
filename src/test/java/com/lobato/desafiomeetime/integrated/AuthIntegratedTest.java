package com.lobato.desafiomeetime.integrated;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.lobato.desafiomeetime.ApplicationTests;
import com.lobato.desafiomeetime.config.properties.HubSpotProperties;
import com.lobato.desafiomeetime.config.utils.JsonUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthIntegratedTest extends ApplicationTests {

    private static final String URL_REDIRECT = "https://app.hubspot.com/oauth/authorize?client_id=%s&redirect_uri=%s&scope=%s";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HubSpotProperties properties;


    @Test
    @DisplayName("Teste de chamada de criação de url")
    void testControllerAuth() throws Exception {

        mockMvc.perform(get("/autenticar"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", URL_REDIRECT
                        .formatted(properties.getClientId(), properties.getRedirectUri(), properties.getScope())));
    }

    @Test
    @DisplayName("Teste de Obter Access Token")
    void testGetAccessToken() throws Exception {

        WireMock.stubFor(WireMock.post(WireMock.urlPathMatching("/oauth/v1/token"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(JsonUtils
                                .getJsonContentFromFile("external/responses", "access-token"))));


        mockMvc.perform(get("/autenticar/auth-callback")
                        .param("code", "mockCode"))
                .andExpect(status().isCreated())
                .andExpect(content().json(JsonUtils
                        .getJsonContentFromFile("internal/responses", "access-token")));
    }
}
