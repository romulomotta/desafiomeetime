package com.lobato.desafiomeetime.config.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public abstract class HeaderUtils {

    public static HttpHeaders createHeaderWithBearer(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token));

        return headers;
    }
}
