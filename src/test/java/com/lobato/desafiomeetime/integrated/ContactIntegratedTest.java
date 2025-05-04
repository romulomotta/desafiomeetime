package com.lobato.desafiomeetime.integrated;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.lobato.desafiomeetime.ApplicationTests;
import com.lobato.desafiomeetime.config.utils.JsonUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ContactIntegratedTest extends ApplicationTests {

    private static final String TOKEN = "CLu5yszoMhINAAEAQAAAAQAAAAAAARibg9oXIKPv";
    private static final String REFRESH = "bf59-bd59-4e6c-985d";
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Teste para criação de contatos")
    void testCreateContact() throws Exception {

        WireMock.stubFor(WireMock.post(WireMock.urlPathMatching("/crm/v3/objects/contacts"))
                .willReturn(WireMock.aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(JsonUtils
                                .getJsonContentFromFile(
                                        "external/responses", "create-contact"))));


        mockMvc.perform(post("/contatos/criar")
                        .header("Authorization", TOKEN)
                        .header("refresh", REFRESH)
                        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.getJsonContentFromFile(
                                "internal/requests", "create-contact-request")))
                .andExpect(status().isCreated())
                .andExpect(content().json(JsonUtils
                        .getJsonContentFromFile("internal/responses", "create-contact")));
    }

    @Test
    @DisplayName("Teste para criação de contatos com Retry")
    void testRetryCreateContact() throws Exception {

        WireMock.stubFor(WireMock.post(WireMock.urlPathMatching("/crm/v3/objects/contacts"))
                .inScenario("Retry Scenario")
                .whenScenarioStateIs(Scenario.STARTED)
                .willReturn(WireMock.aResponse().withStatus(429))
                .willSetStateTo("Second Attempt"));

        WireMock.stubFor(WireMock.post(WireMock.urlPathMatching("/crm/v3/objects/contacts"))
                .inScenario("Retry Scenario")
                .whenScenarioStateIs("Second Attempt")
                .willReturn(WireMock.aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(JsonUtils.getJsonContentFromFile("external/responses", "create-contact"))));


        mockMvc.perform(post("/contatos/criar")
                        .header("Authorization", TOKEN)
                        .header("refresh", REFRESH)
                        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.getJsonContentFromFile(
                                "internal/requests", "create-contact-request")))
                .andExpect(status().isCreated())
                .andExpect(content().json(JsonUtils
                        .getJsonContentFromFile("internal/responses", "create-contact")));

        WireMock.verify(2, WireMock.postRequestedFor(WireMock.urlPathMatching("/crm/v3/objects/contacts")));
    }

    @Test
    @DisplayName("Teste para criação de contatos, precisando atualizar token")
    void testRefreshTokenCreateContact() throws Exception {

        WireMock.stubFor(WireMock.post(WireMock.urlPathMatching("/crm/v3/objects/contacts"))
                .inScenario("Refresh Scenario")
                .whenScenarioStateIs(Scenario.STARTED)
                .willReturn(WireMock.aResponse().withStatus(401))
                .willSetStateTo("Second Attempt"));

        WireMock.stubFor(WireMock.post(WireMock.urlPathMatching("/oauth/v1/token"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(JsonUtils
                                .getJsonContentFromFile("external/responses", "access-token"))));

        WireMock.stubFor(WireMock.post(WireMock.urlPathMatching("/crm/v3/objects/contacts"))
                .inScenario("Refresh Scenario")
                .whenScenarioStateIs("Second Attempt")
                .willReturn(WireMock.aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(JsonUtils.getJsonContentFromFile("external/responses", "create-contact"))));


        mockMvc.perform(post("/contatos/criar")
                        .header("Authorization", TOKEN)
                        .header("refresh", REFRESH)
                        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.getJsonContentFromFile(
                                "internal/requests", "create-contact-request")))
                .andExpect(status().isCreated())
                .andExpect(content().json(JsonUtils
                        .getJsonContentFromFile("internal/responses", "create-contact")));

        WireMock.verify(2, WireMock.postRequestedFor(WireMock.urlPathMatching("/crm/v3/objects/contacts")));
    }
}
