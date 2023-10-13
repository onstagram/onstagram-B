package com.onstagram.post.entity;

import com.onstagram.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="post")
@AllArgsConstructor
public class PostEntity {

    public PostEntity() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_sequence")
    @SequenceGenerator(name = "post_sequence", sequenceName = "post_sequence", allocationSize = 1)
    @Column(name="post_id")
    private Long id;

    @ManyToOne//회원(1) : 게시물(many)
    @JoinColumn(name = "USER_ID")
    private UserEntity userEntity; //회원테이블 참조(외래키)

    @Column(nullable = false, columnDefinition = "CLOB")
    private String caption;

    @CreatedDate
    private LocalDate postDate;

    @PrePersist //엔티티를 만들고 persist(save) 메서드를 호출할 때
    public void onPrePersist(){
        this.postDate = LocalDate.now();
    }

}