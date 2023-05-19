package com.mjc.school.service.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDTORequest;
import com.mjc.school.service.dto.AuthorDTOResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.mapper.AuthorMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mjc.school.service.enums.ConstantValidators.ENTITY_NOT_FOUND_MESSAGE;

@Service
public class AuthorServiceImpl implements AuthorService {
        private final AuthorRepository authorRepository;
        private final AuthorMapper mapper;
        private final String entityName = "Author";

        @Autowired
        public AuthorServiceImpl(AuthorRepository authorRepository){
            this.authorRepository = authorRepository;
            this.mapper = Mappers.getMapper(AuthorMapper.class);
        }

    @Override
    public List<AuthorDTOResponse> readAll(int page, int size, String sortBy) {
        var authorList = authorRepository.readAll(page, size, sortBy);
        return mapper.modelListToDtoList(authorList);
    }

    @Override
    public AuthorDTOResponse readById(Long id) {
        return authorRepository.readById(id)
                .map(mapper::modelToDto)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(
                                        ENTITY_NOT_FOUND_MESSAGE.getContent(),entityName, id
                                )
                        )
                );
    }

    @Override
    public AuthorDTOResponse create(AuthorDTORequest createRequest) {
        AuthorModel model = new AuthorModel();
        model.setName(createRequest.getName());
        var created = authorRepository.create(model);
        return mapper.modelToDto(created);

    }

    @Override
    public AuthorDTOResponse update(AuthorDTORequest updateRequest) {
        long id = updateRequest.getId();
        Optional<AuthorModel> authorModel = authorRepository.readById(id);
        if (authorModel.isPresent()){
            authorModel.get().setName(updateRequest.getName());
            AuthorModel update = authorRepository.update(authorModel.get());
            return mapper.modelToDto(update);
        }
        throw new NotFoundException(
                String.format(
                        ENTITY_NOT_FOUND_MESSAGE.getContent(), entityName, id
                )
        );
    }
    @Override
    public boolean deleteById(Long id) {
        var model = authorRepository.readById(id);
        if (model.isPresent()) {
            return authorRepository.deleteById(id);
        }
        throw new NotFoundException(
                String.format(
                        ENTITY_NOT_FOUND_MESSAGE.getContent(),entityName, id
                )
        );
    }

    @Override
    public AuthorDTOResponse getReference(Long id) {
        var reference = authorRepository.readById(id);
        System.out.println(reference);
        if (reference.isPresent()) {
            return mapper.modelToDto(reference.get());
        }
        throw new NotFoundException(
                String.format(
                        ENTITY_NOT_FOUND_MESSAGE.getContent(), entityName, id
                )
        );
    }


    @Override
    public AuthorDTOResponse readByNewsId(Long id) {
        Optional<AuthorModel> authorModel = authorRepository.readByNewsId(id);
        return mapper.modelToDto(authorModel.get());

    }
}
