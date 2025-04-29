package com.lobato.desafiomeetime.application;

import com.lobato.desafiomeetime.application.domain.ContactRequestDomain;
import com.lobato.desafiomeetime.entrypoint.dto.ContactRequestDto;
import com.lobato.desafiomeetime.entrypoint.dto.ContactResponseDto;
import com.lobato.desafiomeetime.repository.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ContactMapper {

    public abstract ContactResponseDto toDto(ContactResponseEntity entity);

    public ContactRequestEntity toEntity(ContactRequestDomain domain, String category, Integer typeId) {

        var types = List.of(new TypesEntity(category, typeId));
        var to = new AssociateToEntity(domain.associateId());
        var associations = List.of(new AssociationEntity(types, to));
        var properties = new PropertiesEntity(domain.email(), domain.lastname(), domain.firstname());
        return new ContactRequestEntity(associations, properties);
    }

    @Mapping(source = "token", target = "token")
    @Mapping(source = "refresh", target = "refreshToken")
    @Mapping(source = "dto.email", target = "email")
    @Mapping(source = "dto.lastname", target = "lastname")
    @Mapping(source = "dto.firstname", target = "firstname")
    @Mapping(source = "dto.associateId", target = "associateId")
    public abstract ContactRequestDomain toRequestDomain(ContactRequestDto dto, String token, String refresh);
}
