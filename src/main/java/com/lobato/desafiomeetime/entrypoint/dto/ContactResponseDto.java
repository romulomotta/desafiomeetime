package com.lobato.desafiomeetime.entrypoint.dto;

public record ContactResponseDto(String createdAt,
                                 Boolean archived,
                                 String archivedAt,
                                 PropertiesHistoryDto propertiesWithHistory,
                                 String id,
                                 String objectWriteTraceId,
                                 PropertiesDto properties,
                                 String updatedAt) {
}
