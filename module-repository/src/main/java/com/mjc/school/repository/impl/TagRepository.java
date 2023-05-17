package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Repository
public class TagRepository implements BaseRepository<TagModel, Long> {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    @Autowired
    public TagRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }


    @Override
    public List<TagModel> readAll(int page, int size, String sortBy) {
        String[] sortParams = sortBy.split("::");
        String jpql = String.format(
                "FROM Tag nm ORDER BY nm.%s %s", sortParams[0], (sortParams.length > 1) ? sortParams[1] : ""
        );
        Query query = entityManager.createQuery(jpql)
                .setMaxResults(size)
                .setFirstResult(page * size);

        return query.getResultList();
    }

    @Override
    public Optional<TagModel> readById(Long id) {
            return Optional.ofNullable(entityManager.find(TagModel.class, id));
    }

    @Override
    public TagModel create(TagModel entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return entity;
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
        throw new UnsupportedOperationException("Creating Tag entity failed");
    }

    @Override
    public TagModel update(TagModel entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public boolean deleteById(Long id) {
        entityManager.getTransaction().begin();
        TagModel tag = entityManager.find(TagModel.class, id);
        if (tag != null) {
            for (NewsModel news : tag.getNews()) {
                news.getTags().remove(tag);
            }
            tag.getNews().clear();
            entityManager.remove(tag);
        }
        entityManager.getTransaction().commit();
        return true;
}

    @Override
    public boolean existById(Long id) {
        TagModel tagModel = entityManager.find(TagModel.class, id);
        return tagModel != null;
    }

    @Override
    public TagModel findById(Long id) {
        return entityManager.find(TagModel.class, id);
    }

    @Override
    public Optional<TagModel> getReference(Long id) {
        return Optional.ofNullable(entityManager.getReference(TagModel.class, id));
    }

    @Override
    public List<TagModel> getTagsByNewsId(Long id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagModel> query = builder.createQuery(TagModel.class);
        Root<NewsModel> root = query.from(NewsModel.class);
        Join<NewsModel, TagModel> join = root.join("tags");
        query.select(join).where(builder.equal(root.get("id"), id));
        return entityManager.createQuery(query).getResultList();
    }
}
