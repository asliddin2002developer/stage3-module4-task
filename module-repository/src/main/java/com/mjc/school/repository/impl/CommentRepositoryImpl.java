package com.mjc.school.repository.impl;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.model.impl.CommentModel;
import com.mjc.school.repository.model.impl.NewsModel;
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
public class CommentRepositoryImpl extends AbstractDBRepository<CommentModel, Long> implements CommentRepository {

    protected CommentRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public List<CommentModel> readByNewsId(Long newsId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CommentModel> criteriaQuery = criteriaBuilder.createQuery(CommentModel.class);

        Root<CommentModel> root = criteriaQuery.from(CommentModel.class);

        Join<CommentModel, NewsModel> news = root.join("news");

        criteriaQuery.where(
                criteriaBuilder.equal(news.get("id"), newsId)
        );

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
