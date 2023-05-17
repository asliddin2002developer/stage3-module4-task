package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TagDTOResponse {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return "TagDtoResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
