package com.borad.domain.borard;

import com.borad.domain.borard.dto.BoardParamDto;
import com.borad.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {

    @Test
    @DisplayName("게시판이 제대로 생성이 되었는지")
    public void createBoard() {
        BoardParamDto boardParamDto = BoardParamDto.builder()
                .title("제목")
                .contents("내용")
                .mockMvcBuilderBoardParamDto();
        Board board = Board.createBoard(saveMember(), boardParamDto);

        assertEquals(board.getTitle(), boardParamDto.getTitle());
        assertEquals(board.getContents(), boardParamDto.getContents());
    }

    @Test
    @DisplayName("게시판이 제대로 수정이 되었는지")
    public void updateBoard() {
        BoardParamDto boardParamDto = BoardParamDto.builder()
                .title("제목")
                .contents("내용")
                .mockMvcBuilderBoardParamDto();
        Board board = Board.createBoard(saveMember(), boardParamDto);
        BoardParamDto updatePramDto = BoardParamDto.builder()
                .title("수정")
                .contents("내용수정")
                .mockMvcBuilderBoardParamDto();
        board.updateBoard(updatePramDto);

        assertEquals(board.getTitle(), updatePramDto.getTitle());
        assertEquals(board.getContents(), updatePramDto.getContents());
    }


    private Member saveMember() {
        return Member.builder()
                .name("테스트")
                .nickname("테스트")
                .email("테스트")
                .password("test1234")
                .phone("01012345678")
                .mockMvcBuilderMember();
    }
}