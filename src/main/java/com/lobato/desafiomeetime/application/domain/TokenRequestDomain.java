package com.lobato.desafiomeetime.application.domain;

public record TokenRequestDomain(String accessCode, boolean isRefresh) {
}
