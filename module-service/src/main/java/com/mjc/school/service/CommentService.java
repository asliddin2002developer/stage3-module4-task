package com.mjc.school.service;

import com.mjc.school.service.dto.CommentDTORequest;
import com.mjc.school.service.dto.CommentDTOResponse;

import java.util.List;

public interface CommentService extends BaseService<CommentDTORequest, CommentDTOResponse, Long> {

    List<CommentDTOResponse> readByNewsId(Long id);
}
