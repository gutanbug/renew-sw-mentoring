package com.renew.sw.mentoring.domain.user.model.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseUserInfoDto {
    private final String studentId;
    private final String username;
    private final String nickname;
    private final String teamName;
    private final boolean isAdmin;
}
