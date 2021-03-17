package com.borad.controller;


import com.borad.domain.member.dto.MemberDto;
import com.borad.domain.member.dto.MemberParamDto;
import com.borad.domain.member.dto.MemberSearchDto;
import com.borad.domain.member.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/api/member", consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 회원 가입
     * @param memberParamDto
     * @param bindingResult
     * @return
     */
    @PostMapping
    public ResponseEntity createMember(@Valid @RequestBody MemberParamDto memberParamDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.info("code : " + objectError.getCode());
                log.info("defaultMessage : " + objectError.getDefaultMessage());
                log.info("objectName : " + objectError.getObjectName());
            });
            return ResponseEntity.badRequest().body(bindingResult);
        }

        MemberDto saveMember = memberService.createMember(memberParamDto);

        return ResponseEntity.ok().body(saveMember);
    }

    /**
     * 회원 조회
     *
     * @param memberId
     * @return
     */
    @GetMapping("/{memberId}")
    public ResponseEntity getMember(@PathVariable Long memberId) {
        MemberDto member = memberService.findByMember(memberId);

        if (member == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(member);
    }

    /**
     * 회원정보 조회(페이징)
     *
     * @param searchDto
     * @param pageable
     * @param assembler
     * @return
     */
    @GetMapping
    public ResponseEntity queryMembers(MemberSearchDto searchDto, Pageable pageable) {

        MemberPages memberPages = new MemberPages(memberService.findAll(searchDto, pageable));

        return ResponseEntity.ok().body(memberPages);
    }

    /**
     * 회원 정보 수정
     *
     * @param memberId
     * @param memberParamDto
     * @param bindingResult
     * @return
     */
    @PutMapping("/{memberId}")
    public ResponseEntity updateMember(@PathVariable Long memberId,
                                       @RequestBody @Valid MemberParamDto memberParamDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.info("code : " + objectError.getCode());
                log.info("defaultMessage : " + objectError.getDefaultMessage());
                log.info("objectName : " + objectError.getObjectName());
            });
            return ResponseEntity.badRequest().body(bindingResult);
        }

        MemberDto member = memberService.findByMember(memberId);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }

        MemberDto updateMember = memberService.updateMember(memberId, memberParamDto);

        return ResponseEntity.ok().body(updateMember);
    }

    /**
     * 회원 정보 삭제
     *
     * @param memberId
     * @return
     */
    @DeleteMapping("/{memberId}")
    public ResponseEntity<MemberDto> deleteMember(@PathVariable Long memberId) {
        MemberDto member = memberService.findByMember(memberId);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }

        memberService.deleteMember(memberId);

        return ResponseEntity.ok().body(member);
    }

    @Data
    static class MemberPages {
        private Page<MemberDto> page;

        public MemberPages(Page<MemberDto> memberPages) {
            this.page = memberPages;
        }
    }

}