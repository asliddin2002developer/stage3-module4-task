package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDBRepository<T extends BaseEntity<K>, K> implements BaseRepository<T, K> {

    private final Class<T> entityClass;

    private EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    @Autowired
    protected AbstractDBRepository(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManagerFactory.createEntityManager();
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        entityClass = (Class<T>) type.getActualTypeArguments()[0];
    }


    @Override
    public List<T> readAll(int page, int size, String sortBy){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Optional<T> readById(K id){
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public T create(T createRequest){
        entityManager.getTransaction().begin();
        entityManager.persist(createRequest);
        entityManager.getTransaction().commit();
        return createRequest;
    }

    @Override
    public T update(T updateRequest){
        entityManager.getTransaction().begin();
        var updated = entityManager.merge(updateRequest);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return updated;
    }

    @Override
    public boolean deleteById(K id){
        Optional<T> entityRef = getReference(id);
        if (entityRef.isEmpty()){
            return false;
        }
        entityManager.getTransaction().begin();
        entityManager.remove(entityRef.get());
        entityManager.getTransaction().commit();
        return true;
    }

    public boolean existById(K id){
        return entityManager.find(entityClass, id) != null;
    }

    public Optional<T> getReference(K id){
        return Optional.ofNullable(entityManager.getReference(this.entityClass, id));
    }
}
