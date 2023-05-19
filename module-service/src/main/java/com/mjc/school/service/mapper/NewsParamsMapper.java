package com.mjc.school.service.mapper;

import com.mjc.school.repository.utils.NewsSearchParams;
import com.mjc.school.service.dto.NewsParamsRequest;
import com.mjc.school.service.dto.NewsParamsResponse;
import org.mapstruct.Mapper;


@Mapper
public interface NewsParamsMapper {
    NewsSearchParams dtoToModel(NewsParamsRequest newsParamsRequest);
    NewsParamsResponse modelToDto(NewsSearchParams newsSearchParams);
}
