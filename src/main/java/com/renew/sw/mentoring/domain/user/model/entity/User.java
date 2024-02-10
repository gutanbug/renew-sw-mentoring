package com.renew.sw.mentoring.domain.user.model.entity;

import com.renew.sw.mentoring.domain.team.model.entity.Team;
import com.renew.sw.mentoring.domain.user.model.UserRole;
import com.renew.sw.mentoring.global.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "user", indexes = {
        @Index(name = "idx_student_id", columnList = "student_id"),
        @Index(name = "idx_nickname", columnList = "nickname")
})
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @NotBlank
    @Column
    private String studentId;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @NotBlank
    @Column(length = 20)
    private String name;

    @NotBlank
    @Column(length = 20)
    private String nickname;

    @Builder
    private User(@NotNull Team team,
                @NotNull String studentId,
                @NotNull String password,
                @NotNull String name,
                @NotNull String nickname,
                UserRole userRole) {
        this.team = team;
        this.studentId = studentId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.userRole = userRole;
    }
}
