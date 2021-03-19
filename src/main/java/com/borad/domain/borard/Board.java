package com.borad.domain.borard;

import com.borad.domain.borard.dto.BoardParamDto;
import com.borad.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Table(name = "boards")
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 1000)
    private String contents;

    @CreationTimestamp
    @Column(name = "create_dt")
    private LocalDateTime createDt; // 생성일시
    @UpdateTimestamp
    @Column(name = "update_dt")
    private LocalDateTime updateDt; // 수정일시

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;


    public Board(String title, String contents, Member member) {
        this.title = title;
        this.contents = contents;
        this.member = member;
    }

    public static Board createBoard(Member member, BoardParamDto boardParamDto){
        return new Board(boardParamDto.getTitle(), boardParamDto.getContents(), member);
    }

    public Board updateBoard(BoardParamDto boardParamDto){
        this.title = boardParamDto.getTitle();
        this.contents = boardParamDto.getContents();
        return this;
    }
}