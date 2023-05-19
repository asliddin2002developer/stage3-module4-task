package com.mjc.school.service.impl;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.impl.CommentRepositoryImpl;
import com.mjc.school.repository.model.impl.CommentModel;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDTORequest;
import com.mjc.school.service.dto.CommentDTOResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.mapper.CommentMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mjc.school.service.enums.ConstantValidators.ENTITY_NOT_FOUND_MESSAGE;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper mapper;
    private final String entityName = "Comment";

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
        this.mapper = Mappers.getMapper(CommentMapper.class);
    }

    @Override
    public List<CommentDTOResponse> readAll(int page, int size, String sortBy) {
        var modelList = commentRepository.readAll(page, size, sortBy);
        return mapper.modelListToDtoList(modelList);
    }

    @Override
    public CommentDTOResponse readById(Long id) {
        Optional<CommentModel> commentModel = commentRepository.readById(id);
        if (commentModel.isPresent()){
            return mapper.modelToDto(commentModel.get());
        }
        throw new NotFoundException(
                String.format(
                        ENTITY_NOT_FOUND_MESSAGE.getContent(), entityName, id
                )
        );
    }

    @Override
    public CommentDTOResponse create(CommentDTORequest createRequest) {
        var model = mapper.dtoToModel(createRequest);
        var created = commentRepository.create(model);
        return mapper.modelToDto(created);
    }

    @Override
    public CommentDTOResponse update(CommentDTORequest updateRequest) {
        var model = mapper.dtoToModel(updateRequest);
        var updated = commentRepository.update(model);
        return mapper.modelToDto(updated);
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public CommentDTOResponse getReference(Long id) {
        var reference = commentRepository.getReference(id);
        if (reference.isPresent()){
            return mapper.modelToDto(reference.get());
        }
        throw new NotFoundException(
                String.format(
                        ENTITY_NOT_FOUND_MESSAGE.getContent(), entityName, id
                )
        );
    }

    @Override
    public List<CommentDTOResponse> readByNewsId(Long id){
        List<CommentModel> commentssByNewsId = commentRepository.readByNewsId(id);
        return mapper.modelListToDtoList(commentssByNewsId);
    }
}
