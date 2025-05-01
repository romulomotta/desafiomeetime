package com.lobato.desafiomeetime.application.domain;

public record ContactRequestDomain(String token,
                                   String refreshToken,
                                   String email,
                                   String lastname,
                                   String firstname) {
}
