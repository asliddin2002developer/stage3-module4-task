package com.mjc.school.controller;

import com.mjc.school.controller.annotations.CommandBody;
import com.mjc.school.controller.annotations.CommandHandler;
import com.mjc.school.controller.annotations.CommandParam;
import com.mjc.school.service.dto.AuthorDTOResponse;
import com.mjc.school.service.dto.NewsDTOResponse;
import com.mjc.school.service.dto.NewsParamsRequest;
import com.mjc.school.service.dto.TagDTOResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseController<T, R, K> {

    @CommandHandler
    ResponseEntity<List<R>> readAll(@CommandParam("page") int page, @CommandParam("size") int size, @CommandParam("sortBy") String sortBy);

    @CommandHandler
    ResponseEntity<R> readById(@CommandParam("id") K id);

    @CommandHandler
    ResponseEntity<R> create(@CommandBody T createRequest);

    @CommandHandler
    ResponseEntity<R> update(@CommandParam("id") K id, @CommandBody T updateRequest);

    @CommandHandler
    void deleteById(@CommandParam("id") K id);
    @CommandHandler
    default ResponseEntity<AuthorDTOResponse> getAuthorByNewsId(@CommandParam("newsId") Long newsId){
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
    @CommandHandler
    default ResponseEntity<List<TagDTOResponse>> getTagsByNewsId(@CommandParam("newsId") java.lang.Long newsId){
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
    @CommandHandler
    default ResponseEntity<List<NewsDTOResponse>> getNewsByParams(@CommandBody NewsParamsRequest params) {
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
}
