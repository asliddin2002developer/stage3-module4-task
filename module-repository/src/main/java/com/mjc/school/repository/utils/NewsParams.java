package com.mjc.school.repository.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Component
@Scope("prototype")
public class NewsParams {
    String authorName;
    String title;
    String content;
    List<Long> tagIds;
    List<String> tagNames;

}
