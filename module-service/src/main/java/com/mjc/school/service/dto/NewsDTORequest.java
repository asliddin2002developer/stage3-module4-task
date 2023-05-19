package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTORequest {
        private Long id;
        private String title;
        private String content;
        private Long authorId;
        private Set<Long> tagIds;

        public NewsDTORequest(String title, String content, Long authorId, Set<Long> tagIds){
            this.title = title;
            this.content = content;
            this.authorId = authorId;
            this.tagIds = tagIds;
        }



    @Override
        public String toString() {
            return "NewsDto{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", authorId=" + authorId +
                    '}';
        }
}


