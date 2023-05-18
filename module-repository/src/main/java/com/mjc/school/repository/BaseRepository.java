package com.mjc.school.repository;

import com.mjc.school.repository.model.BaseEntity;
import com.mjc.school.repository.utils.NewsParams;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends BaseEntity<K>, K> {

    List<T> readAll(int page, int size, String sortBy);

    Optional<T> readById(K id);

    T create(T entity);

    T update(T entity);

    boolean deleteById(K id);

    boolean existById(K id);
    T findById(K id);
    Optional<T> getReference(K id);


    default T getAuthorByNewsId(K id){
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
    default List<T> getTagsByNewsId(K id){
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
    default List<T> getCommentssByNewsId(K id){
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
    default List<T> getNewsByParams(NewsParams params) {
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }

}
