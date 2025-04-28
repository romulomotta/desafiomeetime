package com.lobato.desafiomeetime.application.domain;

public record AccessTokenDomain(String refreshToken,
                                String accessToken,
                                Integer expiresIn) {
}
