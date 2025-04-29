package com.lobato.desafiomeetime.entrypoint.dto;

public record AdditionalPropertiesDto(String sourceId,
                                      String sourceType,
                                      String sourceLabel,
                                      Integer updatedByUserId,
                                      String value,
                                      String timestamp) {
}
