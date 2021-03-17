package com.borad.domain.borard.repository;

import com.borad.domain.borard.dto.BoardDto;
import com.borad.domain.borard.dto.BoardSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    /**
     * 회원 페이징 형태로 조회
     *
     * @param boardSearchDto
     * @param pageable
     * @return
     */
    Page<BoardDto> findAll(BoardSearchDto boardSearchDto, Pageable pageable);
}
