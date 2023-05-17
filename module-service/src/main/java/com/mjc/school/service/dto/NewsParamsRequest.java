package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsParamsRequest {
    String authorName;
    String title;
    String content;
    List<Long> tagIds;
    List<String> tagNames;
}
