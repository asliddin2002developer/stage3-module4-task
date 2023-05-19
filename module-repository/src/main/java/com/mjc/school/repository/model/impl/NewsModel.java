package com.mjc.school.repository.model.impl;

import com.mjc.school.repository.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "NEWS")
public class NewsModel implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 30)
    @Column(name = "title", nullable = false)
    private String title;

    @Size(min = 5, max = 255)
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "Create_Date", nullable = false)
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "Last_Update_Date", nullable = false)
    @LastModifiedDate
    private LocalDateTime lastUpdateDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "author", nullable = false)
    private AuthorModel author;

    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY)
//    @org.hibernate.annotations.BatchSize(size = 20)  // uses IN in sql query to overcome n + 1 query problem
    @org.hibernate.annotations.Fetch(FetchMode.SUBSELECT) // uses subselect
    private Set<CommentModel> comments = new HashSet<>();

    @ManyToMany
//    @org.hibernate.annotations.BatchSize(size = 20)
    @org.hibernate.annotations.Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "News_Tag",
            joinColumns = { @JoinColumn(name = "News_Id")},
            inverseJoinColumns = { @JoinColumn(name = "Tag_Id")}
    )
    private Set<TagModel> tags = new HashSet<>();

    public NewsModel(String title, String content, AuthorModel author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void addTags(TagModel tag){
        this.tags.add(tag);
    }

    public void addComments(CommentModel commment){
        this.comments.add(commment);
    }

    @Override
    public String toString() {
        return "NewsModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", author=" + author +
                ", comments=" + comments +
                ", tags=" + tags +
                '}';
    }
}
