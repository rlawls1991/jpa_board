package com.borad.domain.borard.dto;

import com.borad.domain.borard.Board;
import com.borad.domain.member.dto.MemberDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDto {
    private Long boardId;
    private String title;
    private String contents;
    private LocalDateTime createDt; // 생성일시
    private LocalDateTime updateDt; // 수정일시

    @QueryProjection
    public BoardDto(Long boardId, String title, String contents, LocalDateTime createDt, LocalDateTime updateDt) {
        this.boardId = boardId;
        this.title = title;
        this.contents = contents;
        this.createDt = createDt;
        this.updateDt = updateDt;
    }

    public static BoardDto createMemberDto(final Board board) {
        return new BoardDto(board.getBoardId(), board.getTitle(), board.getContents(), board.getCreateDt(), board.getUpdateDt());
    }
}
