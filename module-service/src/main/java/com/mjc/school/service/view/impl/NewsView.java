package com.mjc.school.service.view.impl;


import com.mjc.school.service.dto.NewsDTOResponse;
import com.mjc.school.service.view.View;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsView implements View<NewsDTOResponse, List<NewsDTOResponse>> {

    public void display(NewsDTOResponse news){
       System.out.println(news);
    }

    public void displayAll(List<NewsDTOResponse> news){
        news.forEach(System.out::println);
    }
}
