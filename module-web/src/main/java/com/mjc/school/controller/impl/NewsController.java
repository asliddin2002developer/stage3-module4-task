package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDTORequest;
import com.mjc.school.service.dto.NewsDTOResponse;
import com.mjc.school.service.dto.NewsParamsRequest;
import com.mjc.school.service.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController implements BaseController<NewsDTORequest, NewsDTOResponse, Long> {
    private final NewsService model;
    private final View<NewsDTOResponse, List<NewsDTOResponse>> view;

    @Autowired
    public NewsController(NewsService model,
                          View<NewsDTOResponse, List<NewsDTOResponse>> view) {
        this.model = model;
        this.view = view;
    }

    @GetMapping
    public ResponseEntity<List<NewsDTOResponse>> readAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                         @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                                         @RequestParam(value = "sort_by", required = false, defaultValue = "title") String sortBy)  {
        var newsList = model.readAll(page, size, sortBy);
        view.displayAll(newsList);
        return new ResponseEntity<>(newsList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<NewsDTOResponse> readById(@PathVariable("id") Long id) {
        var newsDTOResponse = model.readById(id);
        view.display(newsDTOResponse);
        return new ResponseEntity<>(newsDTOResponse, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<NewsDTOResponse> create(@RequestBody NewsDTORequest createRequest) {
        var newsDTOResponse = model.create(createRequest);
        view.display(newsDTOResponse);
        return new ResponseEntity<>(newsDTOResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsDTOResponse> update(@PathVariable Long id,
                                                  @RequestBody NewsDTORequest updateRequest) {
        var newsDTOResponse = model.update(updateRequest);
        view.display(newsDTOResponse);
        return new ResponseEntity<>(newsDTOResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        model.deleteById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<NewsDTOResponse>> searchNews(@RequestParam(value = "title", required = false) String title,
                                                            @RequestParam(value = "author", required = false) String author,
                                                            @RequestParam(value = "content", required = false) String content,
                                                            @RequestParam(value = "tagIds", required = false) List<Long> tagIds,
                                                            @RequestParam(value = "tagNames", required = false) List<String> tagNames,
                                                            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                                            @RequestParam(value = "sort_by", required = false, defaultValue = "title") String sortBy) {
        NewsParamsRequest newsParamsRequest = new NewsParamsRequest(author, title, content, tagIds, tagNames);
        List<NewsDTOResponse> newsDTORespons = model.readByQueryParams(newsParamsRequest);
        view.displayAll(newsDTORespons);
        return new ResponseEntity<>(newsDTORespons, HttpStatus.OK);
    }


}
