package com.mjc.school.service.view.impl;

import com.mjc.school.service.dto.TagDTOResponse;
import com.mjc.school.service.view.View;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagView implements View<TagDTOResponse, List<TagDTOResponse>> {
    @Override
    public void display(TagDTOResponse tagDtoResponse) {
        System.out.println(tagDtoResponse);
    }

    @Override
    public void displayAll(List<TagDTOResponse> tagDTORespons) {
        tagDTORespons.forEach(System.out::println);
    }
}
