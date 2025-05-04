package com.lobato.desafiomeetime.application;

import com.lobato.desafiomeetime.application.domain.ContactRequestDomain;
import com.lobato.desafiomeetime.entrypoint.dto.ContactRequestDto;
import com.lobato.desafiomeetime.entrypoint.dto.ContactResponseDto;
import com.lobato.desafiomeetime.entrypoint.dto.SavedContactResponseDto;
import com.lobato.desafiomeetime.repository.external.entity.ContactRequestEntity;
import com.lobato.desafiomeetime.repository.external.entity.ContactResponseEntity;
import com.lobato.desafiomeetime.repository.external.entity.PropertiesEntity;
import com.lobato.desafiomeetime.repository.internal.entity.ContactEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigInteger;

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

    public abstract ContactEntity toDataEntity(BigInteger objectId);

    public abstract SavedContactResponseDto toWebhookContactDto(ContactEntity entity);
}
