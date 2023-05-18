package com.mjc.school.controller;

import com.mjc.school.controller.annotations.CommandBody;
import com.mjc.school.controller.annotations.CommandHandler;
import com.mjc.school.controller.annotations.CommandParam;
import com.mjc.school.service.dto.NewsParamsRequest;
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
    default ResponseEntity<R> getAuthorByNewsId(@CommandParam("newsId") Long newsId){
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
    @CommandHandler
    default ResponseEntity<List<R>> getTagsByNewsId(@CommandParam("newsId") Long newsId){
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
    @CommandHandler
    default ResponseEntity<List<R>> getCommentsByNewsId(@CommandParam("newsId") Long newsId){
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
    @CommandHandler
    default ResponseEntity<List<R>> getNewsByParams(@CommandBody NewsParamsRequest params) {
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
}
