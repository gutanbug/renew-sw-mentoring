package com.renew.sw.mentoring.domain.mission.model.entity;

import com.renew.sw.mentoring.domain.completedmission.model.entity.CompletedMission;
import com.renew.sw.mentoring.domain.mission.model.Difficulty;
import com.renew.sw.mentoring.domain.mission.model.MissionStatus;
import com.renew.sw.mentoring.global.base.BaseEntity;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Mission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    @NotBlank
    private String name;

    @Lob
    private String description;

    @NotNull
    private int point;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    private MissionStatus missionStatus;

    @OneToMany(mappedBy = "mission")
    private List<BonusMission> bonusMissions = new ArrayList<>();

    @OneToMany(mappedBy = "mission")
    private List<CompletedMission> completedMissions = new ArrayList<>();

    @Builder
    private Mission(@NotNull String name,
                    String description,
                    @NotNull int point,
                    Difficulty difficulty,
                    MissionStatus missionStatus) {
        this.name = name;
        this.description = description;
        this.point = point;
        this.difficulty = difficulty;
        this.missionStatus = missionStatus;
    }
}
