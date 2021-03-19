package com.borad.controller;


import com.borad.domain.borard.dto.BoardDto;
import com.borad.domain.borard.dto.BoardMemberDto;
import com.borad.domain.borard.dto.BoardParamDto;
import com.borad.domain.borard.dto.BoardSearchDto;
import com.borad.domain.borard.service.BoardService;
import com.borad.domain.member.dto.MemberDto;
import com.borad.domain.member.service.MemberService;
import com.borad.error.ErrorsResource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/api/", consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BoardApiController {

    private final MemberService memberService;
    private final BoardService boardService;

    /**
     * 게시판 등록
     *
     * @param boardParamDto
     * @param errors
     * @return
     */
    @PostMapping("{memberId}/board")
    public ResponseEntity<?> createBoard(@PathVariable Long memberId, @Valid @RequestBody BoardParamDto boardParamDto, Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        MemberDto member = memberService.findByMember(memberId);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }

        BoardDto board = boardService.createBoard(memberId, boardParamDto);

        return ResponseEntity.ok().body(board);
    }

    /**
     * 게시판 하나 조회
     *
     * @param boardId
     * @return
     */
    @GetMapping("/member/board/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable Long boardId) {
        BoardDto board = boardService.findByBoard(boardId);
        if (board == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(board);
    }

    /**
     * 게시판 페이징 조회
     *
     * @param boardSearchDto
     * @param pageable
     * @return
     */
    @GetMapping("/member/board")
    public ResponseEntity<?> queryBoards(BoardSearchDto boardSearchDto, Pageable pageable) {

        BoardPages boardPages = new BoardPages(boardService.finaAll(boardSearchDto, pageable));

        return ResponseEntity.ok().body(boardPages);
    }

    /**
     * 게시판 수정
     *
     * @param boardParamDto
     * @param errors
     * @return
     */
    @PutMapping("/member/board/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable Long boardId, @Valid @RequestBody BoardParamDto boardParamDto, Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        BoardDto board = boardService.findByBoard(boardId);
        if (board == null) {
            return ResponseEntity.notFound().build();
        }
        BoardDto updateBoard = boardService.updateBoard(boardId, boardParamDto);

        return ResponseEntity.ok().body(updateBoard);
    }

    /**
     * 게시판 삭제
     *
     * @param boardId
     * @return
     */
    @DeleteMapping("/member/board/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId) {
        BoardDto board = boardService.findByBoard(boardId);
        if (board == null) {
            return ResponseEntity.notFound().build();
        }
        boardService.deleteBoard(boardId);

        return ResponseEntity.ok().body(board);
    }

    @Data
    static class BoardPages {
        private Page<BoardMemberDto> page;

        public BoardPages(Page<BoardMemberDto> page) {
            this.page = page;
        }
    }

    private ResponseEntity<?> badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }
}
