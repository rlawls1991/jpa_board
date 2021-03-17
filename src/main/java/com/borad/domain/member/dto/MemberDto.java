package com.borad.domain.member.dto;

import com.borad.domain.member.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberDto {
    private Long memberId;
    private String name;
    private String nickname;
    private String phone;
    private String email;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;

    @QueryProjection
    public MemberDto(Long memberId, String name, String nickname, String phone, String email,
                     LocalDateTime createDt, LocalDateTime updateDt) {
        this.memberId = memberId;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.email = email;
        this.createDt = createDt;
        this.updateDt = updateDt;
    }

    public static MemberDto createMemberDto(final Member member) {
        return new MemberDto(member.getMemberId(), member.getName(), member.getNickname(), member.getPhone(), member.getEmail(), member.getCreateDt(), member.getUpdateDt());
    }
}