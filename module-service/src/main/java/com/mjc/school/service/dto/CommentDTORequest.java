package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDTORequest {
    private Long id;
    private String content;

    public CommentDTORequest(String content) {
        this.content = content;
    }
}
