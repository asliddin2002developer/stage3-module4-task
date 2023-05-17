package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDTORequest {
    private Long id;
    private String name;

    public TagDTORequest(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "TagDtoRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
