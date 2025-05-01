package com.lobato.desafiomeetime.application;

import com.lobato.desafiomeetime.application.domain.ContactRequestDomain;
import com.lobato.desafiomeetime.entrypoint.dto.ContactRequestDto;
import com.lobato.desafiomeetime.entrypoint.dto.ContactResponseDto;
import com.lobato.desafiomeetime.repository.entity.ContactRequestEntity;
import com.lobato.desafiomeetime.repository.entity.ContactResponseEntity;
import com.lobato.desafiomeetime.repository.entity.PropertiesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ContactMapper {

    public abstract ContactResponseDto toDto(ContactResponseEntity entity);

    public ContactRequestEntity toEntity(ContactRequestDomain domain) {
        var properties = new PropertiesEntity(domain.email(), domain.lastname(), domain.firstname());
        return new ContactRequestEntity(properties);
    }

    @Mapping(source = "token", target = "token")
    @Mapping(source = "refresh", target = "refreshToken")
    @Mapping(source = "dto.email", target = "email")
    @Mapping(source = "dto.lastname", target = "lastname")
    @Mapping(source = "dto.firstname", target = "firstname")
    public abstract ContactRequestDomain toRequestDomain(ContactRequestDto dto, String token, String refresh);
}
