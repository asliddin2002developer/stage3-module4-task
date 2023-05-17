package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.impl.CommentModel;
import com.mjc.school.service.dto.CommentDTORequest;
import com.mjc.school.service.dto.CommentDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<CommentDTOResponse> modelListToDtoList(List<CommentModel> commentModelList);

    @Mappings(value = {
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createDate", source = "createDate"),
            @Mapping(target = "lastUpdateDate", source = "lastUpdateDate"),
    })
    CommentDTOResponse modelToDto(CommentModel commentModel);
    @Mappings(value = {
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdateDate", ignore = true)
    })
    CommentModel dtoToModel(CommentDTORequest commentDTORequest);

}
