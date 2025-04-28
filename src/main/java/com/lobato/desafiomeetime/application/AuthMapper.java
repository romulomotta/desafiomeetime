package com.lobato.desafiomeetime.application;

import com.lobato.desafiomeetime.application.domain.AccessTokenDomain;
import com.lobato.desafiomeetime.entrypoint.dto.AccessTokenDto;
import com.lobato.desafiomeetime.repository.entity.TokenResponseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    AccessTokenDomain toDomain(TokenResponseEntity entity);

    AccessTokenDto toDto(AccessTokenDomain domain);
}
