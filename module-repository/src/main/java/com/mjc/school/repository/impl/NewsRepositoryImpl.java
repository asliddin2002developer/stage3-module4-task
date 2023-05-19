package com.mjc.school.repository.impl;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.repository.utils.NewsSearchParams;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NewsRepositoryImpl extends AbstractDBRepository<NewsModel, Long> implements NewsRepository {

    protected NewsRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public List<NewsModel> readByQueryParams(NewsSearchParams params) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<NewsModel> criteriaQuery = criteriaBuilder.createQuery(NewsModel.class);

        Root<NewsModel> root = criteriaQuery.from(NewsModel.class);

        List<Predicate> predicates = new ArrayList<>();

        String authorName = params.getAuthorName();
        if (authorName != null && !authorName.equals("")){
            Join<AuthorModel, NewsModel> author = root.join("author");
            predicates.add(criteriaBuilder.equal(author.get("name"), authorName));
        }

        String title = params.getTitle();
        if (title != null && !title.equals("")){
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
        }

        String content = params.getContent();
        if (content != null && !content.equals("")){
            predicates.add(criteriaBuilder.like(root.get("content"), "%" + content + "%"));
        }

        List<Long> tagIds = params.getTagIds();
        List<String> tagNames = params.getTagNames();
        if (tagIds != null || tagNames != null){
            Join<TagModel, NewsModel> tags = root.join("tags");
            if (tagIds != null) {
                predicates.add(tags.get("id").in(tagIds));
            }
            if (tagNames != null){
                predicates.add(tags.get("name").in(tagNames));
            }
        }

        criteriaQuery.select(root).distinct(true).where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
