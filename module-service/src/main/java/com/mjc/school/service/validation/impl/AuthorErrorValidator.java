package com.mjc.school.service.validation.impl;

import com.mjc.school.service.dto.AuthorDTORequest;
import com.mjc.school.service.enums.ConstantValidators;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class AuthorErrorValidator implements Validator<AuthorDTORequest> {

    @Override
    public boolean isValidParams(AuthorDTORequest authorDtoRequest){
        if (authorDtoRequest.getName().length() < 3 || authorDtoRequest.getName().length() > 15){
            throw new NotFoundException(ConstantValidators.AUTHOR_NAME_LENGTH_IS_NOT_VALID.getContent());
        }
        return true;
    }

}