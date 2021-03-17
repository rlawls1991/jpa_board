package com.borad.controller;


import com.borad.domain.borard.dto.BoardDto;
import com.borad.domain.borard.dto.BoardParamDto;
import com.borad.domain.borard.dto.BoardSearchDto;
import com.borad.domain.borard.service.BoardService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/api/board", consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BoardApiController {

    private static BoardService boardService;

    /**
     * 게시판 등록
     *
     * @param boardParamDto
     * @param bindingResult
     * @return
     */
    @PostMapping
    public ResponseEntity createBoard(@Valid @RequestBody BoardParamDto boardParamDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.info("code : " + objectError.getCode());
                log.info("defaultMessage : " + objectError.getDefaultMessage());
                log.info("objectName : " + objectError.getObjectName());
            });
            return ResponseEntity.badRequest().body(bindingResult);
        }

        BoardDto board = boardService.createBoard(boardParamDto);

        return ResponseEntity.ok().body(board);
    }

    /**
     * 회원 한명 조회
     *
     * @param boardId
     * @return
     */
    @GetMapping("/{boardId}")
    public ResponseEntity getBoard(@PathVariable Long boardId) {
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
    @GetMapping
    public ResponseEntity queryBoards(BoardSearchDto boardSearchDto, Pageable pageable) {

        BoardPages boardPages = new BoardPages(boardService.finaAll(boardSearchDto, pageable));

        return ResponseEntity.ok().body(boardPages);
    }


    /**
     * 게시판 수정
     *
     * @param boardParamDto
     * @param bindingResult
     * @return
     */
    @PutMapping("/{boardId}")
    public ResponseEntity updateBoard(@PathVariable Long boardId, @Valid @RequestBody BoardParamDto boardParamDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.info("code : " + objectError.getCode());
                log.info("defaultMessage : " + objectError.getDefaultMessage());
                log.info("objectName : " + objectError.getObjectName());
            });
            return ResponseEntity.badRequest().body(bindingResult);
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
    @DeleteMapping("/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable Long boardId) {
        BoardDto board = boardService.findByBoard(boardId);
        if (board == null) {
            return ResponseEntity.notFound().build();
        }
        boardService.deleteBoard(boardId);

        return ResponseEntity.ok().body(board);
    }

    @Data
    static class BoardPages {
        private Page<BoardDto> page;

        public BoardPages(Page<BoardDto> page) {
            this.page = page;
        }
    }
}
