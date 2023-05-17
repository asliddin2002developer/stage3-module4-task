package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.impl.CommentModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Repository
public class CommentRepository implements BaseRepository<CommentModel, Long> {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Autowired
    protected CommentRepository(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public List<CommentModel> readAll(int page, int size, String sortBy) {
        String[] sortParams = sortBy.split("::");

        CriteriaBuilder cb = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<CommentModel> query = cb.createQuery(CommentModel.class);
        Root<CommentModel> fromItem = query.from(CommentModel.class);

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
    public Optional<CommentModel> readById(Long id) {
        return Optional.ofNullable(entityManager.find(CommentModel.class, id));
    }

    @Override
    public CommentModel create(CommentModel entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return getReference(entity.getId()).get();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
        throw new UnsupportedOperationException("Persisting instance comment failed");
    }

    @Override
    public CommentModel update(CommentModel entity) {
        entityManager.getTransaction().begin();
        var updated = entityManager.merge(entity);
        entityManager.getTransaction().commit();
        return updated;
    }

    @Override
    public boolean deleteById(Long id) {
        var query = entityManager.createQuery("DELETE FROM CommentModel WHERE id = :id")
                .setParameter("id", id);
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean existById(Long id) {
        var query = entityManager.find(CommentModel.class, id);
        return query != null;
    }

    @Override
    public CommentModel findById(Long id) {
        return entityManager.find(CommentModel.class, id);
    }

    @Override
    public Optional<CommentModel> getReference(Long id) {
        return Optional.ofNullable(entityManager.getReference(CommentModel.class, id));
    }
}
