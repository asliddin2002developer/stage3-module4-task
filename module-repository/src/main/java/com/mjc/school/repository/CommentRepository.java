package com.mjc.school.repository;

import com.mjc.school.repository.model.impl.CommentModel;

import java.util.List;

public interface CommentRepository extends BaseRepository<CommentModel, Long>{

    List<CommentModel> readByNewsId(Long id);
}
