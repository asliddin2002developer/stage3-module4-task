package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.NewsModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Repository
public class AuthorRepository implements BaseRepository<AuthorModel, Long> {


    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;


    @Autowired
    public AuthorRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }



    @Override
    public List<AuthorModel> readAll(int page, int size, String sortBy) {
        String[] sortParams = sortBy.split("::");

        CriteriaBuilder cb = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<AuthorModel> query = cb.createQuery(AuthorModel.class);
        Root<AuthorModel> fromItem = query.from(AuthorModel.class);

        if (sortParams.length > 1 && "desc".equals(sortParams[1])){
            query.orderBy(cb.desc(fromItem.get(sortParams[0])));
        }else {
            query.orderBy(cb.asc(fromItem.get(sortParams[0])));
        }

        return entityManager.createQuery(query)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();

    }

    @Override
    public Optional<AuthorModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(AuthorModel.class, id));
    }

    @Override
    public AuthorModel create(AuthorModel entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return getReference(entity.getId()).get();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
        throw new UnsupportedOperationException("Creating Author entity failed");
    }

    @Override
    public AuthorModel update(AuthorModel entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        return entity;

    }

    @Override
    public boolean deleteById(Long id) {
        entityManager.getTransaction().begin();
        var query = entityManager.createQuery("DELETE FROM NewsModel n WHERE n.author.id = :authorId").setParameter("authorId", id);
        query.executeUpdate();
        AuthorModel authorModel = entityManager.find(AuthorModel.class, id);
        entityManager.remove(authorModel);
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    public boolean existById(Long id) {
        AuthorModel authorModel = entityManager.find(AuthorModel.class, id);
        return authorModel != null;
    }


    @Override
    public AuthorModel findById(Long id) {
        return entityManager.find(AuthorModel.class, id);

    }

    @Override
    public Optional<AuthorModel> getReference(Long id) {
        return Optional.ofNullable(entityManager.getReference(AuthorModel.class, id));
    }

    @Override
    public AuthorModel getAuthorByNewsId(Long id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AuthorModel> query = builder.createQuery(AuthorModel.class);
        Root<NewsModel> root = query.from(NewsModel.class);
        Join<NewsModel, AuthorModel> join = root.join("author");
        query.select(join).where(builder.equal(root.get("id"), id));
        return entityManager.createQuery(query).getSingleResult();
    }

}



