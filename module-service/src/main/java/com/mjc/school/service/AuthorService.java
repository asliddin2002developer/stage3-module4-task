package com.mjc.school.service;

import com.mjc.school.service.dto.AuthorDTORequest;
import com.mjc.school.service.dto.AuthorDTOResponse;

public interface AuthorService extends BaseService<AuthorDTORequest, AuthorDTOResponse, Long> {
    AuthorDTOResponse readByNewsId(Long id);
}
