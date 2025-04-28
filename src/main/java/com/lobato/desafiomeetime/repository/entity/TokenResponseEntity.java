package com.lobato.desafiomeetime.repository.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponseEntity(@JsonProperty("refresh_token") String refreshToken,
                                  @JsonProperty("access_token") String accessToken,
                                  @JsonProperty("expires_in") Integer expiresIn) {
}
