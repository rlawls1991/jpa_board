package com.borad.domain.borard.repository;



import com.borad.domain.borard.dto.MemberDto;
import com.borad.domain.borard.dto.MemberSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MemberRepositoryCustom {

    /**
     * 회원 한명의 정보를 조회
     *
     * @param memberId
     * @return
     */
    MemberDto findByMember(final Long memberId);


    /**
     * 회원 페이징 형태로 조회
     *
     * @param memberSearchDto
     * @param pageable
     * @return
     */
    Page<MemberDto> findAll(final MemberSearchDto memberSearchDto, final Pageable pageable);

}
