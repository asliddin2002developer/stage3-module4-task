package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.service.dto.AuthorDTORequest;
import com.mjc.school.service.dto.AuthorDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "authors", source = "authors")
    List<AuthorDTOResponse> modelListToDtoList(List<AuthorModel> authorModelList);

    @Mappings(value = {
            @Mapping(target = "createDate", source = "createDate"),
            @Mapping(target = "lastUpdateDate", source = "lastUpdateDate"),
            @Mapping(target = "id", source = "id")
    })
    AuthorDTOResponse modelToDto(AuthorModel authorModel);

    @Mappings(value = {
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdateDate", ignore = true),
            @Mapping(target = "news", ignore = true),
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
    })
    AuthorModel dtoToModel(AuthorDTORequest author);
}
