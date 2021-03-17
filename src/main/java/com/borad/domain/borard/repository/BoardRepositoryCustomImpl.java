package com.borad.domain.borard.repository;

import com.borad.domain.borard.dto.BoardDto;
import com.borad.domain.borard.dto.BoardSearchDto;
import com.borad.domain.borard.dto.QBoardDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.borad.domain.borard.QBoard.board;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Page<BoardDto> findAll(BoardSearchDto boardSearchDto, Pageable pageable) {
        QueryResults<BoardDto> result = query
                .select(new QBoardDto(
                        board.boardId,
                        board.title,
                        board.contents,
                        board.createDt,
                        board.updateDt
                ))
                .from(board)
                .where(
                        titleLike(boardSearchDto.getTitle())
                        , contentsLike(boardSearchDto.getContents())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createDt.desc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression titleLike(final String title) {
        if (title == null) {
            return null;
        }
        return board.title.like(title);
    }

    private BooleanExpression contentsLike(final String contents) {
        if (contents == null) {
            return null;
        }
        return board.contents.like(contents);
    }
}
