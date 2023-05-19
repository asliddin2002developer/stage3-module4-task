package com.mjc.school.service.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.CommentModel;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.service.NewsService;
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
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final TagRepository tagRepository;
    private final AuthorRepository authorRepository;
    private final NewsMapper mapper;
    private final NewsParamsMapper newsParamsMapper;
    private final String entityName = "News";
    private final String authorEntity = "Author";

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository,
                           TagRepository tagRepositoryImpl,
                           AuthorRepository authorRepository){
        this.newsRepository = newsRepository;
        this.tagRepository = tagRepositoryImpl;
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

            if (tagIds != null) {
                //add tags to newsModel
                for (Long id : tagIds) {
                    var tag = tagRepository.readById(id);
                    tag.ifPresent(newsModel::addTags);
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
    public List<NewsDTOResponse> readByQueryParams(NewsParamsRequest params) {
        List<NewsModel> newsModels = newsRepository.readByQueryParams(newsParamsMapper.dtoToModel(params));
        return mapper.modelListToDtoList(newsModels);
    }

}
