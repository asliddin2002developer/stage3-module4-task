package com.mjc.school.service.validation.impl;

import com.mjc.school.service.dto.NewsDTORequest;
import com.mjc.school.service.enums.ConstantValidators;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.exception.ValidatorException;
import com.mjc.school.service.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class NewsErrorValidator implements Validator<NewsDTORequest> {


    @Override
    public boolean isValidParams(NewsDTORequest request){
        String title = request.getTitle();
        String content = request.getContent();
        Long author = request.getAuthorId();


        if (title.length() < 5 || request.getTitle().length() >= 30) {
            throw new ValidatorException(ConstantValidators.TITLE_LENGTH_VALIDATOR.getContent());
        } else if (content.length() < 5 || request.getContent().length() >= 255) {
            throw new ValidatorException(ConstantValidators.CONTENT_LENGTH_VALIDATOR.getContent());
        }else if (author == null) {
            throw new NotFoundException(ConstantValidators.AUTHOR_NOT_FOUND_VALIDATOR.getContent());
        }
        return true;
    }

}
