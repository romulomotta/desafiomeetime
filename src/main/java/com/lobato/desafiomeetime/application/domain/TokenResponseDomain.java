package com.lobato.desafiomeetime.application.domain;

public record TokenResponseDomain(String refreshToken,
                                  String accessToken,
                                  Integer expiresIn) {
}
