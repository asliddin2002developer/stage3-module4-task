package com.mjc.school.controller;

import com.mjc.school.controller.annotations.CommandBody;
import com.mjc.school.controller.annotations.CommandHandler;
import com.mjc.school.controller.annotations.CommandParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BaseController<T, R, K> {

    @CommandHandler
    ResponseEntity<List<R>> readAll(@CommandParam("page") int page, @CommandParam("size") int size, @CommandParam("sortBy") String sortBy);

    @CommandHandler
    ResponseEntity<R> readById(@CommandParam("id") K id);

    @CommandHandler
    ResponseEntity<R> create(@CommandBody T createRequest);

    @PutMapping("/{id}")
    ResponseEntity<R> update(@PathVariable K id,
                             @RequestBody T updateRequest);


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable Long id);
}
