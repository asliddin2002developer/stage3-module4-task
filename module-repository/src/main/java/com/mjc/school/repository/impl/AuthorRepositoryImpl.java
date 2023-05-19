package com.mjc.school.repository.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.NewsModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl extends AbstractDBRepository<AuthorModel, Long> implements AuthorRepository {

    protected AuthorRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public Optional<AuthorModel> readByNewsId(Long newsId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AuthorModel> query = builder.createQuery(AuthorModel.class);

        Root<AuthorModel> root = query.from(AuthorModel.class);

        Join<AuthorModel, NewsModel> news = root.join("news");
        query.where(
                builder.equal(news.get("id"), newsId)
        );
        return Optional.ofNullable(entityManager.createQuery(query).getSingleResult());
    }
}
