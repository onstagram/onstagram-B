package com.onstagram.Member.entity;

import com.onstagram.Member.domain.ModifyDto;
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
@Table(name = "member")
@AllArgsConstructor
public class MemberEntity {

    public MemberEntity() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userPhone;

    private String userImg;

    private String introduction;

    @CreatedDate
    private LocalDate userDate;

    @PrePersist
    public void onPrePersist() {
        this.userDate = LocalDate.now();
    }

    public void modifyEntity(ModifyDto modifyDto) {
        password = modifyDto.getPassword();
        userImg = modifyDto.getUserImg();
        introduction = modifyDto.getIntroduction();
    }

    public void introduction(String introductionDefault) {
        introduction = introductionDefault;
    }


}