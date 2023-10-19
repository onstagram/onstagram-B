package com.onstagram.follow.entity;

import com.onstagram.Member.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@Table(name="follow")
@AllArgsConstructor
public class FollowEntity {

    public FollowEntity() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "follow_sequence")
    @SequenceGenerator(name = "follow_sequence", sequenceName = "follow_sequence", allocationSize = 1)
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private MemberEntity followMemberEntity; //구독(팔로우)당한 사람

    @ManyToOne
    @JoinColumn(name = "following_id")
    private MemberEntity followingMemberEntity; //구독(팔로우)한 사람


    @CreatedDate
    private LocalDate followDate; //팔로우 하고 당한 날짜

    @PrePersist //엔티티를 만들고 persist(save) 메서드를 호출할 때
    public void onPrePersist(){
        this.followDate = LocalDate.now();
    }

}