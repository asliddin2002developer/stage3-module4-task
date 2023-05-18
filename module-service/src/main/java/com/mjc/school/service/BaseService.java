package com.mjc.school.service;

import com.mjc.school.service.dto.AuthorDTOResponse;
import com.mjc.school.service.dto.NewsDTOResponse;
import com.mjc.school.service.dto.NewsParamsRequest;
import com.mjc.school.service.dto.TagDTOResponse;
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

    default R getAuthorByNewsId(K id){
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
    default List<R> getTagsByNewsId(K id){
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
    default List<R> getCommentsByNewsId(K id){
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
    default List<R> getNewsByParams(NewsParamsRequest params) {
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }

}
