package com.borad.controller;

import com.borad.common.RestDocsConfiguration;
import com.borad.domain.borard.dto.BoardParamDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional(readOnly = true)
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class BoardApiControllerTest {

    @Test
    @Transactional
    @DisplayName("정상적으로 게시판을 생성하는 테스트")
    void createBoard() {
        BoardParamDto boardParamDto = BoardParamDto.builder()
                .title("정상적으로 게시판이 생성되었는가?")
                .contents("정상적으로 게시판내용이 생성되었는가???!@!#1231@!#!@##!@")
                .mockMvcBuilderBoardParamDto();

    }

    @Test
    void getBoard() {
    }

    @Test
    void queryBoards() {
    }

    @Test
    void updateBoard() {
    }

    @Test
    void deleteBoard() {
    }
}