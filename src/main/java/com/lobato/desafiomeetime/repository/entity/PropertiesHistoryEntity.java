package com.lobato.desafiomeetime.repository.entity;

import java.util.List;

public record PropertiesHistoryEntity(List<AdditionalPropertiesEntity> additionalProp1,
                                      List<AdditionalPropertiesEntity> additionalProp2,
                                      List<AdditionalPropertiesEntity> additionalProp3) {
}
