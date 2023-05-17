package com.mjc.school.service.dto;

import java.util.List;

public class NewsParamsResponse {
    String authorName;
    String title;
    String content;
    List<Long> tagIds;
    List<String> tagNames;
}
