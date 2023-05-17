package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.service.dto.NewsDTORequest;
import com.mjc.school.service.dto.NewsDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class NewsMapper {

    public abstract List<NewsDTOResponse> modelListToDtoList(List<NewsModel> newsModels);


    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "tagDtos", source = "tags")
    @Mapping(target = "commentDtos", source = "comments")
    public abstract NewsDTOResponse modelToDto(NewsModel request);

    @Mappings({
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdateDate", ignore = true),
            @Mapping(target = "tags", ignore = true),
            @Mapping(target = "author.id", source = "authorId")
    })
   public abstract NewsModel dtoToModel(NewsDTORequest request);

}
