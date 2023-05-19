package com.mjc.school.repository.impl;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Getter
@Setter
@Repository
public class TagRepositoryImpl extends AbstractDBRepository<TagModel, Long> implements TagRepository {
    protected TagRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public List<TagModel> readByNewsId(Long newsId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagModel> criteriaQuery = criteriaBuilder.createQuery(TagModel.class);

        Root<TagModel> root = criteriaQuery.from(TagModel.class);

        Join<TagModel, NewsModel> news = root.join("news");

        criteriaQuery.where(
                criteriaBuilder.equal(news.get("id"), newsId)
        );

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
