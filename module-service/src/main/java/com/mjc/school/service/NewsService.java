package com.mjc.school.service;

import com.mjc.school.service.dto.*;

import java.util.List;

public interface NewsService extends BaseService<NewsDTORequest, NewsDTOResponse, Long> {

    List<NewsDTOResponse> readByQueryParams(NewsParamsRequest params);
}
