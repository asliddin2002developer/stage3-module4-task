package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTORequest {
    private long id;
    private String name;

    public AuthorDTORequest(String name){
        this.name = name;
    }
    @Override
    public String toString() {
        return "AuthorDto{name='" + name + '\'' +
                '}';
    }
}
