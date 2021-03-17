package com.borad.domain.borard;

import com.borad.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Table(name = "boards")
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String Contents;

    @CreationTimestamp
    @Column(name = "create_dt")
    private Date createDt; // 생성일시
    @UpdateTimestamp
    @Column(name = "update_dt")
    private Date updateDt; // 수정일시

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

}
