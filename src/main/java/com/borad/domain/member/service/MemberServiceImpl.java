package com.borad.domain.member.service;


import com.borad.domain.member.Member;
import com.borad.domain.member.dto.MemberDto;
import com.borad.domain.member.dto.MemberParamDto;
import com.borad.domain.member.dto.MemberSearchDto;
import com.borad.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public MemberDto createMember(final MemberParamDto dto) {
        Member member = Member.createMember(dto);
        Member saveMember = memberRepository.save(member);

        return MemberDto.createMemberDto(saveMember);
    }

    @Override
    public MemberDto findByMember(final Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (!member.isPresent()) {
            return null;
        }

        return MemberDto.createMemberDto(member.get());
    }

    @Override
    @Transactional
    public MemberDto updateMember(final Long memberId, final MemberParamDto dto) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (!member.isPresent()) {
            return null;
        }
        member.get().updateMember(dto);

        return MemberDto.createMemberDto(memberRepository.save(member.get()));
    }

    @Override
    public Page<MemberDto> findAll(final MemberSearchDto searchDto, final Pageable pageable) {
        return memberRepository.findAll(searchDto, pageable);
    }

    @Override
    public void deleteMember(final Long id) {
        memberRepository.deleteById(id);
    }
}
