package com.borad.domain.borard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardMemberDto {
    private Long memberId;
    private Long boardId;
    private String name;
    private String nickname;
    private LocalDateTime updateDt; // 수정일시
    private String title;
    private String contents;

    @QueryProjection
    public BoardMemberDto(Long memberId, Long boardId, String name, String nickname, LocalDateTime updateDt, String title, String contents) {
        this.memberId = memberId;
        this.boardId = boardId;
        this.name = name;
        this.nickname = nickname;
        this.updateDt = updateDt;
        this.title = title;
        this.contents = contents;
    }
}