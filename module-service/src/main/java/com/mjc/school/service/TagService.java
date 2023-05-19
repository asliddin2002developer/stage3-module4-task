package com.mjc.school.service;

import com.mjc.school.service.dto.TagDTORequest;
import com.mjc.school.service.dto.TagDTOResponse;

import java.util.List;

public interface TagService extends BaseService<TagDTORequest, TagDTOResponse, Long> {
    List<TagDTOResponse> readByNewsId(Long newsId);
}
