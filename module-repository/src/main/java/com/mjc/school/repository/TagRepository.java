package com.mjc.school.repository;

import com.mjc.school.repository.model.impl.TagModel;

import java.util.List;

public interface TagRepository extends BaseRepository<TagModel, Long> {

    List<TagModel> readByNewsId(Long newsId);
}
