package com.mjc.school.repository.impl;


import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.repository.utils.NewsParams;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;


@Getter
@Setter
@Repository
public class NewsRepository  implements BaseRepository<NewsModel, Long> {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Autowired
    public NewsRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }



    @Override
    public List<NewsModel> readAll(int page, int size, String sortBy) {

            // query for FetchType.LAZY
            // return entityManager.createQuery("SELECT DISTINCT  n FROM NewsModel n " +
            //                                                   "JOIN FETCH n.tags t " +
            //                                                   "JOIN FETCH n.author a ", NewsModel.class).getResultList();
//        return entityManager.createNativeQuery("select * from news", NewsModel.class).getResultList();
        String[] sortParams = sortBy.split("::");
        String jpql = String.format(
                "FROM NewsModel nm ORDER BY nm.%s %s", sortParams[0], (sortParams.length > 1) ? sortParams[1] : ""
        );
        Query query = entityManager.createQuery(jpql)
                .setMaxResults(size)
                .setFirstResult(page * size);

        return query.getResultList();
    }

    @Override
    public Optional<NewsModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(NewsModel.class, id));

    }

    @Override
    public NewsModel create(NewsModel entity) {
        try {
            entityManager.getTransaction().begin();

            entityManager.persist(entity);

            entityManager.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
    @Override
    public NewsModel update(NewsModel entity) {
        entityManager.getTransaction().begin();
        NewsModel newsModel = entityManager.merge(entity);
        entityManager.getTransaction().commit();
        return newsModel;
    }
    @Override
    public boolean deleteById(Long id) {
        entityManager.getTransaction().begin();

        NewsModel news = entityManager.find(NewsModel.class, id);

         //   get all tags that news has alone
         Query q = entityManager.createNativeQuery("SELECT nt.tag_id FROM News_Tag nt " +
                                                                         "JOIN tag t ON nt.tag_id = t.id " +
                                                                         "JOIN News_Tag nt2 ON t.id = nt2.tag_id " +
                                                                         "WHERE nt2.news_id = ? " +
                                                                         "GROUP BY nt.tag_id " +
                                                                        "HAVING COUNT(nt.news_id) = 1");
         q.setParameter(1, news.getId());
         List<Long> tagIds = (List<Long>) q.getResultList();

        // remove all associations for this news
        q = entityManager.createNativeQuery("DELETE FROM News_Tag nt " +
                                                        "WHERE nt.news_id = :id");
        q.setParameter("id", news.getId());
        q.executeUpdate();

        // remove all tags that this news has alone
        q = entityManager.createNativeQuery("DELETE FROM tag t " +
                                                        "WHERE t.id IN (:ids)");
        q.setParameter("ids", tagIds);
        q.executeUpdate();

        entityManager.remove(news);

        entityManager.getTransaction().commit();
        return true;
    }
    @Override
    public boolean existById(Long id) {
        var query = entityManager.find(NewsModel.class, id);
        return query != null;
    }

    @Override
    public NewsModel findById(Long id){
        return entityManager.find(NewsModel.class, id);
    }

    @Override
    public Optional<NewsModel> getReference(Long id) {
        return Optional.ofNullable(entityManager.getReference(NewsModel.class, id));
    }

    @Override
    public List<NewsModel> getNewsByParams(NewsParams params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<NewsModel> cq = cb.createQuery(NewsModel.class);
        Root<NewsModel> newsRoot = cq.from(NewsModel.class);
        Join<NewsModel, TagModel> tagJoin = newsRoot.join("tags");

        Predicate authorPredicate = null;
        if(params.getAuthorName() != null && !params.getAuthorName().isEmpty()){
            Join<NewsModel, AuthorModel> authorJoin = newsRoot.join("author");
            authorPredicate = cb.like(authorJoin.get("name"), "%" + params.getAuthorName() + "%");
        }

        Predicate titlePredicate = null;
        if(params.getTitle() != null && !params.getTitle().isEmpty()){
            titlePredicate = cb.like(newsRoot.get("title"), "%" + params.getTitle() + "%");
        }

        Predicate contentPredicate = null;
        if(params.getContent() != null && !params.getContent().isEmpty()){
            contentPredicate = cb.like(newsRoot.get("content"), "%" + params.getContent() + "%");
        }

        Predicate tagIdPredicate = null;
        if(params.getTagIds() != null && !params.getTagIds().isEmpty()){
            tagIdPredicate = tagJoin.get("id").in(params.getTagIds());
        }

        Predicate tagNamePredicate = null;
        if(params.getTagNames() != null && !params.getTagNames().isEmpty()){
            tagNamePredicate = tagJoin.get("name").in(params.getTagNames());
        }

        Predicate finalPredicate = cb.and(authorPredicate, titlePredicate, contentPredicate, tagIdPredicate, tagNamePredicate);
        cq.where(finalPredicate);

        return entityManager.createQuery(cq).getResultList();
    }
}
