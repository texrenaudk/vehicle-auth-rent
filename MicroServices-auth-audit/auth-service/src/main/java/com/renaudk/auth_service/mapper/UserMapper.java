package com.renaudk.auth_service.mapper;


import com.renaudk.auth_service.dto.RegisterRequestDto;
import com.renaudk.auth_service.entity.UserEntities;
import com.renaudk.auth_service.dto.UserEntitiesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntitiesDto toDto(UserEntities userEntities);

    UserEntities toEntity(UserEntitiesDto userEntitiesDto);

   // @Mapping(target = "id", ignore = true)
    //@Mapping(target = "roles", ignore = true)
    UserEntities toEntity(RegisterRequestDto dto);
}
