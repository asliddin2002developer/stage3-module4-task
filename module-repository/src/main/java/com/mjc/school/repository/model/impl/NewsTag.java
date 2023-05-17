package com.mjc.school.repository.model.impl;

import com.mjc.school.repository.model.BaseEntity;

import javax.persistence.*;


@Entity
@Table(name = "NEWS_TAG")
public class NewsTag implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "News_Id", nullable = false)
    private NewsModel news;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "Tag_Id", nullable = false)
    private TagModel tagModel;


    @Override
    public String toString() {
        return "NewsTag{" +
                "id=" + id +
                ", news=" + news +
                ", tag=" + tagModel +
                '}';
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
