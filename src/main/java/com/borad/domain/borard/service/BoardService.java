package com.borad.domain.borard.service;

import com.borad.domain.borard.dto.BoardDto;
import com.borad.domain.borard.dto.BoardMemberDto;
import com.borad.domain.borard.dto.BoardParamDto;
import com.borad.domain.borard.dto.BoardSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    /**
     * 게시판 생성
     *
     * @param boardParamDto
     * @return
     */
    BoardDto createBoard(Long memberId, BoardParamDto boardParamDto);

    /**
     * 게시판 조회
     *
     * @param boardId
     * @return
     */
    BoardDto findByBoard(Long boardId);


    /**
     * 게시판 수정
     *
     * @param boardId
     * @param boardParamDto
     * @return
     */
    BoardDto updateBoard(Long boardId, BoardParamDto boardParamDto);

    /**
     * 게시판 페이징 조회
     *
     * @param pageable
     * @return
     */
    Page<BoardMemberDto> finaAll(BoardSearchDto boardSearchDto, Pageable pageable);

    /**
     * 게시판 삭제
     *
     * @param boardId
     */
    void deleteBoard(Long boardId);
}
