package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
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
@RequestMapping("/api/v1/all-news")
public class NewsController implements BaseController<NewsDTORequest, NewsDTOResponse, Long> {
    private final BaseService<NewsDTORequest, NewsDTOResponse, Long> model;
    private final View<NewsDTOResponse, List<NewsDTOResponse>> view;

    @Autowired
    public NewsController(BaseService<NewsDTORequest, NewsDTOResponse, Long> model,
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


    @GetMapping("/news/{id}")
    public ResponseEntity<NewsDTOResponse> readById(@PathVariable("id") Long id) {
        var newsDTOResponse = model.readById(id);
        view.display(newsDTOResponse);
        return new ResponseEntity<>(newsDTOResponse, HttpStatus.OK);
    }

    @PostMapping("/news/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<NewsDTOResponse> create(@RequestBody NewsDTORequest createRequest) {
        var newsDTOResponse = model.create(createRequest);
        view.display(newsDTOResponse);
        return new ResponseEntity<>(newsDTOResponse, HttpStatus.CREATED);
    }

    @PutMapping("/news/update/{id}")
    public ResponseEntity<NewsDTOResponse> update(@PathVariable Long id,
                                                  @RequestBody NewsDTORequest updateRequest) {
        var newsDTOResponse = model.update(updateRequest);
        view.display(newsDTOResponse);
        return new ResponseEntity<>(newsDTOResponse, HttpStatus.OK);
    }

    @DeleteMapping("/news/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        model.deleteById(id);
    }

    @GetMapping("/news/params")
    public ResponseEntity<List<NewsDTOResponse>> getNewsByParams(@RequestBody NewsParamsRequest newsParamsRequest){
        List<NewsDTOResponse> newsDTORespons = model.getNewsByParams(newsParamsRequest);
        view.displayAll(newsDTORespons);
        return new ResponseEntity<>(newsDTORespons, HttpStatus.OK);
    }


}
