package com.lobato.desafiomeetime.repository.external.entity;

public record ContactResponseEntity(String createdAt,
                                    Boolean archived,
                                    String archivedAt,
                                    PropertiesHistoryEntity propertiesWithHistory,
                                    String id,
                                    String objectWriteTraceId,
                                    PropertiesEntity properties,
                                    String updatedAt) {
}
