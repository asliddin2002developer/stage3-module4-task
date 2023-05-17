package com.mjc.school.service.view.impl;

import com.mjc.school.service.dto.CommentDTOResponse;
import com.mjc.school.service.view.View;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentView implements View<CommentDTOResponse, List<CommentDTOResponse>> {
    @Override
    public void display(CommentDTOResponse commentDTOResponse) {
        System.out.println(commentDTOResponse);
    }

    @Override
    public void displayAll(List<CommentDTOResponse> commentDTOResponses) {
        commentDTOResponses.forEach(System.out::println);
    }
}
