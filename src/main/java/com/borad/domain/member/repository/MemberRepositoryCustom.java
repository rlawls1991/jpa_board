package com.borad.domain.member.repository;



import com.borad.domain.member.dto.MemberDto;
import com.borad.domain.member.dto.MemberSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MemberRepositoryCustom {

    /**
     * 회원 페이징 형태로 조회
     *
     * @param memberSearchDto
     * @param pageable
     * @return
     */
    Page<MemberDto> findAll(final MemberSearchDto memberSearchDto, final Pageable pageable);

}
