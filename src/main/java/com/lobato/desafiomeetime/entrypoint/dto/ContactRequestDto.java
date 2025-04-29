package com.lobato.desafiomeetime.entrypoint.dto;

public record ContactRequestDto(String email,
                                String lastname,
                                String firstname,
                                String associateId) {
}
