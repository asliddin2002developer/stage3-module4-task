package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTOResponse {
        private Long id;
        private String title;
        private String content;
        private Long authorId;
        private LocalDateTime createDate;
        private LocalDateTime lastUpdateDate;
        private Set<CommentDTOResponse> commentDtos;
        private Set<TagDTOResponse> tagDtos;


    @Override
    public String toString() {
        return "NewsDtoResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author=" + authorId +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", comments=" + commentDtos +
                ", tags=" + tagDtos +
                '}';
    }
}
