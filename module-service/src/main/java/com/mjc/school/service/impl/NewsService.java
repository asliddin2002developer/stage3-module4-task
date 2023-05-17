package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDTORequest;
import com.mjc.school.service.dto.NewsDTOResponse;
import com.mjc.school.service.dto.NewsParamsRequest;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.mapper.NewsParamsMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mjc.school.service.enums.ConstantValidators.ENTITY_NOT_FOUND_MESSAGE;

@Service
public class NewsService implements BaseService<NewsDTORequest, NewsDTOResponse, Long> {

    private final BaseRepository<NewsModel, Long> newsRepository;
    private final BaseRepository<TagModel, Long> tagRepository;
    private final BaseRepository<AuthorModel, Long> authorRepository;
    private final NewsMapper mapper;
    private final NewsParamsMapper newsParamsMapper;
    private String entityName = "News";
    private String authorEntity = "Author";

    @Autowired
    public NewsService(BaseRepository<NewsModel, Long> newsRepository,
                       BaseRepository<TagModel, Long> tagRepository,
                       BaseRepository<AuthorModel, Long> authorRepository){
        this.newsRepository = newsRepository;
        this.tagRepository = tagRepository;
        this.authorRepository = authorRepository;
        this.newsParamsMapper = Mappers.getMapper(NewsParamsMapper.class);
        this.mapper = Mappers.getMapper(NewsMapper.class);
    }


    @Override

    public List<NewsDTOResponse> readAll(int page, int size, String sortBy) {
        List<NewsModel> newsList = newsRepository.readAll(page, size, sortBy);
        newsList.forEach(System.out::println);
        return mapper.modelListToDtoList(newsList);
    }

    @Override
    public NewsDTOResponse readById(Long id) {
        return newsRepository.readById(id)
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
    public NewsDTOResponse create(NewsDTORequest createRequest) {

            Long authorId = createRequest.getAuthorId();
            AuthorModel authorModel = authorRepository.readById(authorId)
                    .orElseThrow(
                            () -> new NotFoundException(
                                    String.format(
                                            ENTITY_NOT_FOUND_MESSAGE.getContent(), authorEntity, authorId
                                    )
                            )
                    );

            Set<Long> tagIds = createRequest.getTagIds();

            NewsModel newsModel = mapper.dtoToModel(createRequest);
            newsModel.setAuthor(authorModel);
            Optional<TagModel> tag;
            for ( Long id : tagIds ){
                tag = tagRepository.readById(id);
                if (tag.isPresent()) {
                    newsModel.addTags(tag.get());
                }
            }
            newsRepository.create(newsModel);
            return mapper.modelToDto(newsModel);
    }

    @Override
    public NewsDTOResponse update(NewsDTORequest updateRequest) {
        Long newsId = updateRequest.getId();
        NewsModel news = newsRepository.readById(newsId)
                .orElseThrow(
                    () -> new NotFoundException(
                            String.format(
                                    ENTITY_NOT_FOUND_MESSAGE.getContent(), entityName, newsId
                            )
                        )
                    );

        news.setTitle(updateRequest.getTitle());
        news.setContent(updateRequest.getContent());

        Long authorId = updateRequest.getAuthorId();
        AuthorModel author = authorRepository.readById(authorId)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(
                                        ENTITY_NOT_FOUND_MESSAGE.getContent(), authorEntity, authorId
                                )
                            )
                        );
        news.setAuthor(author);

        Set<TagModel> tags = updateRequest.getTagIds()
                                    .stream()
                                        .map(tagRepository::readById)
                                            .filter(Optional::isPresent)
                                            .map(Optional::get)
                                            .collect(Collectors.toSet());
        news.setTags(tags);

        NewsModel updatedNewsModel = newsRepository.update(news);
        return mapper.modelToDto(updatedNewsModel);
    }

    @Override
    public boolean deleteById(Long id) {
        if (newsRepository.existById(id)){
            return newsRepository.deleteById(id);
        }
        throw new NotFoundException(String.format(
                ENTITY_NOT_FOUND_MESSAGE.getContent(), entityName, id
            )
        );
    }

    @Override
    public NewsDTOResponse getReference(Long id) {
        var reference = newsRepository.readById(id);
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
    public List<NewsDTOResponse> getNewsByParams(NewsParamsRequest params) {
        List<NewsModel> newsModels = newsRepository.getNewsByParams(newsParamsMapper.dtoToModel(params));
        return mapper.modelListToDtoList(newsModels);
    }
}
