package com.lobato.desafiomeetime.integration;

import com.lobato.desafiomeetime.ApplicationTests;
import com.lobato.desafiomeetime.config.properties.HubSpotProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TokenIntegrationTest extends ApplicationTests {

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
}
