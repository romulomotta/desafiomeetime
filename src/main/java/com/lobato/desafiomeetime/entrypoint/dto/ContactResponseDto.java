package com.lobato.desafiomeetime.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ContactResponseDto(String createdAt,
                                 Boolean archived,
                                 String archivedAt,
                                 PropertiesHistoryDto propertiesWithHistory,
                                 String id,
                                 String objectWriteTraceId,
                                 PropertiesDto properties,
                                 String updatedAt) {
}
