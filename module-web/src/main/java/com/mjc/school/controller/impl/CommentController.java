package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDTORequest;
import com.mjc.school.service.dto.CommentDTOResponse;
import com.mjc.school.service.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController implements BaseController<CommentDTORequest, CommentDTOResponse, Long> {

    private final CommentService model;
    private final View<CommentDTOResponse, List<CommentDTOResponse>> view;

    @Autowired
    public CommentController(CommentService model,
                             View<CommentDTOResponse, List<CommentDTOResponse>> view) {
        this.model = model;
        this.view = view;
    }


    @Override
    @GetMapping
    public ResponseEntity<List<CommentDTOResponse>> readAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                                            @RequestParam(value = "sort_by", required = false, defaultValue = "content") String sortBy)  {
        var modelList = model.readAll(page, size, sortBy);
        if (modelList == null){
            return new ResponseEntity<>(modelList, HttpStatus.NOT_FOUND);
        }
        view.displayAll(modelList);
        return new ResponseEntity<>(modelList, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTOResponse> readById(@PathVariable Long id) {
        var commentDTOResponse = this.model.readById(id);
//        if (commentDTOResponse == null){
//            return new ResponseEntity<>(commentDTOResponse, HttpStatus.NOT_FOUND);
//        }
        view.display(commentDTOResponse);
        return new ResponseEntity<>(commentDTOResponse, HttpStatus.OK);

    }

    @Override
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<CommentDTOResponse> create(@RequestBody CommentDTORequest createRequest) {
        var commentDTOResponse = model.create(createRequest);
        view.display(commentDTOResponse);
        return new ResponseEntity<>(commentDTOResponse, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CommentDTOResponse> update(@PathVariable Long id,
                                                     @RequestBody CommentDTORequest updateRequest) {
        var commentDTOResponse = model.update(updateRequest);
        view.display(commentDTOResponse);
        return new ResponseEntity<>(commentDTOResponse, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        model.deleteById(id);

    }
    @GetMapping("/news/{id}")
    public ResponseEntity<List<CommentDTOResponse>> getCommentsByNewsId(@PathVariable Long id){
        List<CommentDTOResponse> commentsByNewsId = model.readByNewsId(id);
        view.displayAll(commentsByNewsId);
        return new ResponseEntity<>(commentsByNewsId, HttpStatus.OK);
    }
}
