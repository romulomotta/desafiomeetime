package com.lobato.desafiomeetime.entrypoint.dto;

import java.util.List;

public record PropertiesHistoryDto(List<AdditionalPropertiesDto> additionalProp1,
                                   List<AdditionalPropertiesDto> additionalProp2,
                                   List<AdditionalPropertiesDto> additionalProp3) {
}
