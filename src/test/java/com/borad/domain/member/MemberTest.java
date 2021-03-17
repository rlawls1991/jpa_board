package com.borad.domain.member;

import com.borad.domain.member.dto.MemberParamDto;
import com.borad.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional(readOnly = true)
public class MemberTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원이 제대로 생성이 되었는지")
    public void createMember(){
        MemberParamDto memberParamDto = MemberParamDto.builder()
                .name("테스트")
                .nickname("테스트")
                .email("테스트")
                .password("test1234")
                .phone("01012345678")
                .mockMvcBuilderMemberParamDto();

        Member createMember = Member.createMember(memberParamDto);

        assertEquals(memberParamDto.getEmail(), createMember.getEmail());
        assertEquals(memberParamDto.getName(), createMember.getName());
        assertEquals(memberParamDto.getPassword(), createMember.getPassword());
        assertEquals(memberParamDto.getPhone(), createMember.getPhone());
        assertEquals(memberParamDto.getNickname(), createMember.getNickname());
    }

    @Test
    @DisplayName("회원이 제대로 수정이 되었는지")
    public void updateMember(){
        MemberParamDto memberParamDto = MemberParamDto.builder()
                .name("테스트")
                .nickname("테스트")
                .email("update@test.com")
                .password("test1234")
                .phone("01043215678")
                .mockMvcBuilderMemberParamDto();

        MemberParamDto updateParamDto = MemberParamDto.builder()
                .name("수정")
                .nickname("수정")
                .email("update@test.com")
                .password("update1234")
                .phone("01012345678")
                .mockMvcBuilderMemberParamDto();

        Member member = Member.createMember(memberParamDto);
        member.updateMember(updateParamDto);

        assertEquals(updateParamDto.getEmail(), member.getEmail());
        assertEquals(updateParamDto.getName(), member.getName());
        assertEquals(updateParamDto.getPassword(), member.getPassword());
        assertEquals(updateParamDto.getPhone(), member.getPhone());
        assertEquals(updateParamDto.getNickname(), member.getNickname());
    }

    private Member saveMember() {
        Member member = Member.builder()
                .name("테스트")
                .nickname("테스트")
                .email("테스트")
                .password("test1234")
                .phone("01012345678")
                .mockMvcBuilderMember();

        return memberRepository.save(member);
    }
}