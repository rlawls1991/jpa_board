package com.borad.domain.borard.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class BoardParamDto {
    @NotEmpty
    @Size(min = 1, max = 50, message = "제목의 길이는 최대 50 입니다.")
    private String title;

    @NotEmpty
    @Size(min = 1, max = 1000, message = "본문의 길이는 최대 1000글자 입니다.")
    private String contents;

    @Builder(buildMethodName = "mockMvcBuilderBoardParamDto")
    public BoardParamDto( String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
