package com.lobato.desafiomeetime.application;

import com.lobato.desafiomeetime.application.domain.ContactRequestDomain;
import com.lobato.desafiomeetime.application.domain.TokenRequestDomain;
import com.lobato.desafiomeetime.application.domain.TokenResponseDomain;
import com.lobato.desafiomeetime.config.token.TokenResponseEntity;
import com.lobato.desafiomeetime.entrypoint.dto.TokenResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TokenMapper {

    TokenResponseDomain toDomain(TokenResponseEntity entity);

    TokenResponseDto toDto(TokenResponseDomain domain);

    @Mapping(target = "accessCode", source = "code")
    TokenRequestDomain toRequestTokenDomain(String code, boolean isRefresh);

    @Mapping(source = "requestDomain.refreshToken", target = "accessCode")
    TokenRequestDomain toRequestToken(ContactRequestDomain requestDomain, boolean isRefresh);
}
