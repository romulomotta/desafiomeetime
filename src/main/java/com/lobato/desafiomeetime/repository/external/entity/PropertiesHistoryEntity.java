package com.lobato.desafiomeetime.repository.external.entity;

import java.util.List;

public record PropertiesHistoryEntity(List<AdditionalPropertiesEntity> additionalProp1,
                                      List<AdditionalPropertiesEntity> additionalProp2,
                                      List<AdditionalPropertiesEntity> additionalProp3) {
}
