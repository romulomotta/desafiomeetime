package com.lobato.desafiomeetime.repository.entity;

import java.util.List;

public record AssociationEntity(List<TypesEntity> types,
                                AssociateToEntity to) {
}
