package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.TagDTORequest;
import com.mjc.school.service.dto.TagDTOResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.mapper.TagMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mjc.school.service.enums.ConstantValidators.ENTITY_NOT_FOUND_MESSAGE;

@Service
public class TagService implements BaseService<TagDTORequest, TagDTOResponse, Long> {
    private final BaseRepository<TagModel, Long> tagRepository;
    private final TagMapper mapper;
    private String entityName = "Tag";

    @Autowired
    public TagService(BaseRepository<TagModel, Long> tagRepository) {
        this.tagRepository = tagRepository;
        this.mapper = Mappers.getMapper(TagMapper.class);
    }

    @Override
    public List<TagDTOResponse> readAll(int page, int size, String sortBy) {
        List<TagModel> tagModels = tagRepository.readAll(page, size, sortBy);
        return mapper.modelListToDtoList(tagModels);
    }

    @Override
    public TagDTOResponse readById(Long id) {
        return tagRepository.readById(id)
                .map(mapper::modelToDto)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(
                                        ENTITY_NOT_FOUND_MESSAGE.getContent(), entityName, id
                                )
                        )
                );

    }

    @Override
    public TagDTOResponse create(TagDTORequest createRequest) {
        TagModel author = mapper.dtoToModel(createRequest);
        author = tagRepository.create(author);
        return mapper.modelToDto(author);

    }

    @Override
    public TagDTOResponse update(TagDTORequest updateRequest) {
        Long id = updateRequest.getId();
        Optional<TagModel> tagModel = tagRepository.readById(id);
        if (tagModel.isPresent()){
            tagModel.get().setName(updateRequest.getName());
            TagModel update = tagRepository.update(tagModel.get());
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
        if (tagRepository.existById(id)){
            return tagRepository.deleteById(id);
        }
        throw new NotFoundException(
                String.format(
                        ENTITY_NOT_FOUND_MESSAGE.getContent(), entityName, id
                )
        );
    }

    @Override
    public TagDTOResponse getReference(Long id) {
        var reference = tagRepository.getReference(id);
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
    public List<TagDTOResponse> getTagsByNewsId(Long id){
        List<TagModel> tags = tagRepository.getTagsByNewsId(id);
        return tags.stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }
}
