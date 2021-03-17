package com.borad.domain.member.repository;




import com.borad.domain.member.dto.MemberDto;
import com.borad.domain.member.dto.MemberSearchDto;
import com.borad.domain.member.dto.QMemberDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.borad.domain.member.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Page<MemberDto> findAll(MemberSearchDto memberSearchDto, Pageable pageable) {
        QueryResults<MemberDto> result = query
                .select(new QMemberDto(
                        member.memberId,
                        member.name,
                        member.phone,
                        member.email,
                        member.nickname,
                        member.createDt,
                        member.updateDt
                ))
                .from(member)
                .where(
                        nameEq(memberSearchDto.getName())
                        , emailEq(memberSearchDto.getEmail())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(member.createDt.desc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression memberIdEq(final Long id) {
        if (id <= 0) {
            return null;
        }

        return member.memberId.eq(id);
    }

    private BooleanExpression nameEq(final String name) {
        if (name == null) {
            return null;
        }
        return member.name.eq(name);
    }

    private BooleanExpression emailEq(final String email) {
        if (email == null) {
            return null;
        }
        return member.email.eq(email);
    }
}
