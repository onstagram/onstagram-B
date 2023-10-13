package com.onstagram.user.entity;

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
@Table(name="member")
@AllArgsConstructor
public class UserEntity {

    public UserEntity() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @Column(name = "user_id")
    private Long id;

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

    @PrePersist //엔티티를 만들고 persist(save) 메서드를 호출할 때
    public void onPrePersist(){
        this.userDate = LocalDate.now();
    }

}