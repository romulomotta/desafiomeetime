package com.lobato.desafiomeetime.repository.client;

import com.lobato.desafiomeetime.repository.entity.ContactRequestEntity;
import com.lobato.desafiomeetime.repository.entity.ContactResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ContactClient", url = "${config.ContactClient.url}")
public interface ContactClient {
    @PostMapping(value = "${config.ContactClient.path}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ContactResponseEntity createContact(@RequestHeader HttpHeaders header,
                                        @RequestBody ContactRequestEntity request);
}
