package com.lobato.desafiomeetime.entrypoint.dto;

import java.util.List;

public record AssociationDto(List<TypesDto> types,
                             ToDto to) {
}
