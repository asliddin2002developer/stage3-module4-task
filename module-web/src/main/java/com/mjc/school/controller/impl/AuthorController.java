package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDTORequest;
import com.mjc.school.service.dto.AuthorDTOResponse;
import com.mjc.school.service.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController implements BaseController<AuthorDTORequest, AuthorDTOResponse, Long> {
    private final BaseService<AuthorDTORequest, AuthorDTOResponse, Long> model;
    private final View<AuthorDTOResponse, List<AuthorDTOResponse>> view;

    @Autowired
    public AuthorController(BaseService<AuthorDTORequest, AuthorDTOResponse, Long> model,
                            View<AuthorDTOResponse, List<AuthorDTOResponse>> view) {
        this.model = model;
        this.view = view;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<AuthorDTOResponse>> readAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                           @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                                           @RequestParam(value = "sort_by", required = false, defaultValue = "name") String sortBy)
    {
        var authorDTOResponses = model.readAll(page, size, sortBy);
        view.displayAll(authorDTOResponses);
        return new ResponseEntity<>(authorDTOResponses, HttpStatus.OK);
    }

    @Override
    @GetMapping("/author/{id}")
    public ResponseEntity<AuthorDTOResponse> readById(@PathVariable Long id) {
        var authorDTOResponse = model.readById(id);
        view.display(authorDTOResponse);
        return new ResponseEntity<>(authorDTOResponse, HttpStatus.OK);
    }

    @Override
    @PostMapping("/author/create")
    public ResponseEntity<AuthorDTOResponse> create(@RequestBody AuthorDTORequest createRequest) {
        var authorDTOResponse = model.create(createRequest);
        view.display(authorDTOResponse);
        return new ResponseEntity<>(authorDTOResponse, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/author/update")
    public ResponseEntity<AuthorDTOResponse> update(@RequestBody AuthorDTORequest updateRequest) {
        var authorDTOResponse = model.update(updateRequest);
        view.display(authorDTOResponse);
        return new ResponseEntity<>(authorDTOResponse, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/author/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        var resp = model.deleteById(id);
        System.out.println(resp);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Override
    @GetMapping("/author/newsId/{id}")
    public ResponseEntity<AuthorDTOResponse> getAuthorByNewsId(@PathVariable Long id) {
        var resp = model.getAuthorByNewsId(id);
        view.display(resp);
        return new ResponseEntity<>(resp, HttpStatus.NO_CONTENT);
    }
}