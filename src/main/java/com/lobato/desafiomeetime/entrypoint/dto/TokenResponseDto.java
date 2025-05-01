package com.lobato.desafiomeetime.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponseDto(@JsonProperty("refresh_token")
                               String refreshToken,
                               @JsonProperty("access_token")
                               String accessToken,
                               @JsonProperty("expires_in")
                               Integer expiresIn) {
}
