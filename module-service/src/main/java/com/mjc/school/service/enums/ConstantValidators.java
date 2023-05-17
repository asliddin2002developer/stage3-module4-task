package com.mjc.school.service.enums;


public enum ConstantValidators {
    TITLE_LENGTH_VALIDATOR("Title field should have length of value from 5 to 30"),
    CONTENT_LENGTH_VALIDATOR("Content field should have length of value from 5 to 255"),
    AUTHOR_NOT_FOUND_VALIDATOR("AuthorId should be mapped to the author datasource"),
    AUTHOR_NAME_LENGTH_IS_NOT_VALID("Author name length is not valid"),
    TAG_NAME_LENGTH_IS_NOT_VALID("Tag name length is not valid"),
    ENTITY_NOT_FOUND_MESSAGE("%s with ID %d not found");

    private final String content;

    ConstantValidators(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}