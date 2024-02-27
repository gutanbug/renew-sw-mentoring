package com.renew.sw.mentoring.domain.mission.model.dto.list;

import com.renew.sw.mentoring.domain.mission.model.entity.BonusMission;
import lombok.Getter;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
public class ResponseBonusMissionDto {

    private final Long id;

    private final String name;

    private final String description;

    private final int point;

    public ResponseBonusMissionDto(BonusMission bonusMission) {
        this.id = bonusMission.getId();
        this.name = bonusMission.getName();
        this.description = bonusMission.getDescription();
        this.point = bonusMission.getPoint();
    }

    public static List<ResponseBonusMissionDto> listOf(List<BonusMission> bonusMissions) {
        return bonusMissions.stream()
                .map(ResponseBonusMissionDto::new)
                .collect(toList());
    }
}
