package com.lobato.desafiomeetime.repository.external.entity;

import java.util.List;

public record AssociationEntity(List<TypesEntity> types,
                                AssociateToEntity to) {
}
