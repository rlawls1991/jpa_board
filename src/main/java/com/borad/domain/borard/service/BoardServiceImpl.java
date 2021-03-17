package com.borad.domain.borard.service;

import com.borad.domain.borard.Board;
import com.borad.domain.borard.dto.BoardDto;
import com.borad.domain.borard.dto.BoardParamDto;
import com.borad.domain.borard.dto.BoardSearchDto;
import com.borad.domain.borard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    public BoardDto createBoard(final BoardParamDto boardParamDto) {
        Board createBoard = Board.createBoard(boardParamDto);
        boardRepository.save(createBoard);
        return BoardDto.createMemberDto(createBoard);
    }

    @Override
    public BoardDto findByBoard(final Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if (!optionalBoard.isPresent()) {
            return null;
        }
        return BoardDto.createMemberDto(optionalBoard.get());
    }

    @Override
    public BoardDto updateBoard(Long boardId, BoardParamDto boardParamDto) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.get();
        board.updateBoard(boardParamDto);
        return BoardDto.createMemberDto(board);
    }

    @Override
    public Page<BoardDto> finaAll(final BoardSearchDto boardSearchDto, Pageable pageable) {
        return boardRepository.findAll(boardSearchDto, pageable);
    }

    @Override
    public void deleteBoard(final Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
