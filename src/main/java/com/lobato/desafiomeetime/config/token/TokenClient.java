package com.lobato.desafiomeetime.config.token;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "TokenClient", url = "${config.TokenClient.url}")
public interface TokenClient {
    @PostMapping(value = "${config.TokenClient.path}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenResponseEntity getAccessToken(@RequestBody MultiValueMap<String, String> request);
}
