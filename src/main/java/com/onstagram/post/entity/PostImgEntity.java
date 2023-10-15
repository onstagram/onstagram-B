package com.onstagram.post.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name="post_img")
@AllArgsConstructor
public class PostImgEntity {

    public PostImgEntity() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "img_sequence")
    @SequenceGenerator(name = "img_sequence", sequenceName = "img_sequence", allocationSize = 1)
    @Column(name="img_id")
    private Long imgId;

    private String imgName;

    @ManyToOne //게시물(1) : 이미지(다)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

}
