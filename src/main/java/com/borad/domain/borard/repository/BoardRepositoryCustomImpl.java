package com.borad.domain.borard.repository;

import com.borad.domain.borard.dto.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.borad.domain.borard.QBoard.board;
import static com.borad.domain.member.QMember.member;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Page<BoardMemberDto> findAll(BoardSearchDto boardSearchDto, Pageable pageable) {
        QueryResults<BoardMemberDto> result = query
                .select(new QBoardMemberDto(
                        member.memberId,
                        board.boardId,
                        member.name,
                        member.nickname,
                        board.updateDt,
                        board.title,
                        board.contents
                ))
                .from(member)
                .leftJoin(member.boards, board)
                .where(
                        titleLike(boardSearchDto.getTitle())
                        , contentsLike(boardSearchDto.getContents())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.updateDt.desc())
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
