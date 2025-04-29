package com.lobato.desafiomeetime.repository.entity;

import java.util.List;

public record ContactRequestEntity(List<AssociationEntity> associations,
                                   PropertiesEntity properties) {
}
