package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDTORequest;
import com.mjc.school.service.dto.TagDTOResponse;
import com.mjc.school.service.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController implements BaseController<TagDTORequest, TagDTOResponse, Long> {

    private final TagService model;
    private final View<TagDTOResponse, List<TagDTOResponse>> view;

    @Autowired
    public TagController(TagService model,
                         View<TagDTOResponse, List<TagDTOResponse>> view) {
        this.model = model;
        this.view = view;
    }
    @Override
    @GetMapping
    public ResponseEntity<List<TagDTOResponse>> readAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                        @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                                        @RequestParam(value = "sort_by", required = false, defaultValue = "name") String sortBy)
    {
        var tags = model.readAll(page, size, sortBy);
        view.displayAll(tags);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TagDTOResponse> readById(@PathVariable Long id) {
        var tagDTOResponse = model.readById(id);
        view.display(tagDTOResponse);
        return new ResponseEntity<>(tagDTOResponse, HttpStatus.OK);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TagDTOResponse> create(@RequestBody TagDTORequest createRequest) {
        var tagDTOResponse = model.create(createRequest);
        view.display(tagDTOResponse);
        return new ResponseEntity<>(tagDTOResponse, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<TagDTOResponse> update(@PathVariable Long id,
                                                 @RequestBody TagDTORequest updateRequest) {
        var tagDTOResponse = model.update(updateRequest);
        view.display(tagDTOResponse);
        return new ResponseEntity<>(tagDTOResponse, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        model.deleteById(id);
    }


    @GetMapping("/news/{id}")
    public ResponseEntity<List<TagDTOResponse>> getTagsByNewsId(@PathVariable Long id) {
        var tags = model.readByNewsId(id);
        view.displayAll(tags);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }
}
