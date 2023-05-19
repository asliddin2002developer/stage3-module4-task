package com.mjc.school.repository.model.impl;


import com.mjc.school.repository.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "AUTHOR")
@EntityListeners(AuditingEntityListener.class)
public class AuthorModel implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 15)
    @Column(name = "name", nullable = false)
    private String name;

    @CreatedDate
    @Column(name = "Create_Date", nullable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "Last_Update_Date", nullable = false)
    private LocalDateTime lastUpdateDate;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<NewsModel> news;


    public AuthorModel(String name){
        this.name = name;
    }

    public AuthorModel(String name, LocalDateTime createDate, LocalDateTime lastUpdateDate){
        this.name = name;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public AuthorModel() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorModel that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(createDate, that.createDate) && Objects.equals(lastUpdateDate, that.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createDate, lastUpdateDate);
    }

    @Override
    public String toString() {
        return "AuthorModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
