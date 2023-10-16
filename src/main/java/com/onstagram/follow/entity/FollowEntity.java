package com.onstagram.follow.entity;

import com.onstagram.member.entity.MemberEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "follow")
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "follow_sequence")
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follow_user_id") //follow 하는
    private MemberEntity followUserId;

    @ManyToOne
    @JoinColumn(name = "following_user_id") //follow 당하는
    private MemberEntity followingUserId;

    @CreatedDate
    private LocalDate followDate;


    @PrePersist //엔티티를 만들고 persist(save) 메서드를 호출할 때
    public void onPrePersist() {
        this.followDate = LocalDate.now();
    }

}

