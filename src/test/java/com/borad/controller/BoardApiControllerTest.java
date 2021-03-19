package com.borad.controller;

import com.borad.common.RestDocsConfiguration;
import com.borad.domain.borard.dto.BoardDto;
import com.borad.domain.borard.dto.BoardParamDto;
import com.borad.domain.borard.service.BoardService;
import com.borad.domain.member.Member;
import com.borad.domain.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional(readOnly = true)
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class BoardApiControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardService boardService;

    @Test
    @Transactional
    @DisplayName("정상적으로 게시판을 생성하는 테스트")
    void createBoard() throws Exception {
        // Given
        BoardParamDto boardParamDto = BoardParamDto.builder()
                .title("정상적으로 게시판이 생성되었는가?")
                .contents("정상적으로 게시판내용이 생성되었는가???!@!#1231@!#!@##!@")
                .mockMvcBuilderBoardParamDto();
        Member member = saveMember();

        // When
        ResultActions perform = this.mockMvc.perform(post("/api/{memberId}/board/", member.getMemberId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(boardParamDto)))
                .andDo(print());

        // Then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("title").value(boardParamDto.getTitle()))
                .andExpect(jsonPath("contents").value(boardParamDto.getContents()))
                .andDo(document("게시판 생성"))
                .andDo(print())
        ;
    }

    @Test
    @Transactional
    @DisplayName("입력 받을 수 없는 값을 사용한 경우에 에러가 발생하는 테스트")
    public void createBoard_bad_Request() throws Exception {
        // Given
        Member member = saveMember();

        // When
        ResultActions perform = this.mockMvc.perform(post("/api/{memberId}/board/", member.getMemberId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(member)))
                .andDo(print());
        // Then
        perform.andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @DisplayName("입력값이 비어있는 경우에 에러가 발생하는 테스트")
    public void createBoard_Bad_Request_Empty_Input() throws Exception {
        // Given
        Member member = saveMember();

        // When & Then
        this.mockMvc.perform(post("/api/{memberId}/board/", member.getMemberId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest())
                .andDo(print());
        ;
    }

    @Test
    @Transactional
    @DisplayName("입력값이 잘못들어가 에러가 발생하는 테스트")
    public void createBoard_Bad_Request_Wrong_Input() throws Exception {
        // Given
        Member member = saveMember();
        BoardParamDto boardParamDto = BoardParamDto.builder()
                .title("정상적으로 게시판이 생성되었는가?12344567891234567891234567891234589")
                .contents("정상적으로 게시판내용이 생성되었는가???!@!#1231@!#!@##!@")
                .mockMvcBuilderBoardParamDto();

        // When & Then
        this.mockMvc.perform(post("/api/{memberId}/board/", member.getMemberId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardParamDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
        ;
    }


    @Test
    @Transactional
    @DisplayName("30명의 회원들을 10개식 조회하기")
    public void queryBoards() throws Exception {
        // Given
        IntStream.range(1, 41).forEach(this::createTestBoard);

        // When
        ResultActions perform = this.mockMvc.perform(get("/api/member/board/")
                .param("page", "1")
                .param("size", "10")
                .param("email", "테스트")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // Then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andDo(document("게시판 페이징 조회"))
                .andDo(print())
        ;
    }

    @Test
    @Transactional
    @DisplayName("기존의 게시판를 하나 조회하기")
    public void getBoard() throws Exception {
        // Given
        Member member = saveMember();
        BoardDto saveBoard = saveBoard(1, member);

        // When & Then
        this.mockMvc.perform(get("/api/member/board/{boardId}", saveBoard.getBoardId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value(saveBoard.getTitle()))
                .andExpect(jsonPath("contents").value(saveBoard.getContents()))
                .andDo(document("게시판 하나 조회"))
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("없는 게시판을 조회했을 때 404 응답")
    public void getBoard404() throws Exception {
        // When & Then
        this.mockMvc.perform(get("/api/member/board/{boardId}", 999999)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print())
        ;
    }

    @Test
    @Transactional
    @DisplayName("데이터 수정")
    public void updateBoard() throws Exception {
        // Given
        Member member = saveMember();
        BoardDto saveBoard = saveBoard(1, member);
        BoardParamDto updatePramDto = BoardParamDto.builder()
                .title("수정된 게시판 제목")
                .contents("수정된 게시판 내용")
                .mockMvcBuilderBoardParamDto();

        // When
        ResultActions perform =  this.mockMvc.perform(put("/api/member/board/{boardId}", saveBoard.getBoardId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(updatePramDto)))
                .andDo(print());

        // Then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("title").value(updatePramDto.getTitle()))
                .andExpect(jsonPath("contents").value(updatePramDto.getContents()))
                .andDo(document("게시판 수정"))
                .andDo(print())
        ;
    }


    @Test
    @DisplayName("없는 게시판을 수정했을 때 404 응답")
    public void updateBoard404() throws Exception {
        // Given
        Member member = saveMember();
        BoardDto saveBoard = saveBoard(1, member);
        BoardParamDto updatePramDto = BoardParamDto.builder()
                .title("수정된 게시판 제목")
                .contents("수정된 게시판 내용")
                .mockMvcBuilderBoardParamDto();

        // When & Then
        this.mockMvc.perform(put("/api/member/board/{boardId}", 999999)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(updatePramDto)))
                .andExpect(status().isNotFound())
                .andDo(print())
        ;
    }

    @Test
    @Transactional
    @DisplayName("입력 받을 수 없는 값을 사용한 경우에 에러가 발생한하는 테스트")
    public void updateBoard_Bad_Request() throws Exception {
        // When
        Member member = saveMember();

        // When & Then
        this.mockMvc.perform(put("/api/member/board/{boardId}", 999999)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(member)))
                .andExpect(status().isBadRequest())
                .andDo(print())
        ;
    }

    @Test
    @Transactional
    @DisplayName("게시판 삭제")
    public void deleteBoard() throws Exception {
        // When
        // Given
        Member member = saveMember();
        BoardDto saveBoard = saveBoard(1, member);

        // When & Then
        this.mockMvc.perform(delete("/api/member/board/{boardId}", saveBoard.getBoardId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("title").value(saveBoard.getTitle()))
                .andExpect(jsonPath("contents").value(saveBoard.getContents()))
                .andDo(document("게시판 하나 삭제"))
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }

    @Test
    @Transactional
    @DisplayName("없는 게시판을 삭제했을 때 404 응답")
    public void deleteBoard404() throws Exception {
        // When & Then
        this.mockMvc.perform(delete("/api/member/board/{boardId}", 999999)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print())
        ;
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

    private void createTestBoard(int index) {
        saveBoard(index, saveMember());
    }

    private BoardDto saveBoard(int index, Member member) {
        BoardParamDto boardParamDto = BoardParamDto.builder()
                .title("정상적으로 게시판이 생성되었는가?" + index)
                .contents("정상적으로 게시판내용이 생성되었는가???!@!#1231@!#!@##!@")
                .mockMvcBuilderBoardParamDto();
        return boardService.createBoard(member.getMemberId(), boardParamDto);
    }
}