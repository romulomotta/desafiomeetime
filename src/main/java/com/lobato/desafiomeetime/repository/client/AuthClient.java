package com.lobato.desafiomeetime.repository.client;

import com.lobato.desafiomeetime.repository.entity.TokenResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AuthClient", url = "${config.AuthClient.url}")
public interface AuthClient {
    @PostMapping(value = "${config.AuthClient.path}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenResponseEntity getAccessToken(@RequestBody MultiValueMap<String, String> request);
}
