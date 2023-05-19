package com.mjc.school.service;

import com.mjc.school.service.dto.NewsParamsRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseService<T, R, K> {
    List<R> readAll(int page, int size, String sortBy);

    R readById(K id);

    R create(T createRequest);

    R update(T updateRequest);

    boolean deleteById(K id);
    R getReference(K id);
}
