package com.lobato.desafiomeetime.repository.entity;

public record AdditionalPropertiesEntity(String sourceId,
                                         String sourceType,
                                         String sourceLabel,
                                         Integer updatedByUserId,
                                         String value,
                                         String timestamp) {
}
