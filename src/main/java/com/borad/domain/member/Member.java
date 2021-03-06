package com.borad.domain.member;

import com.borad.domain.borard.Board;


import com.borad.domain.member.dto.MemberParamDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Table(name = "member")
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;
    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 30)
    private String nickname;
    private String password;
    @Column(nullable = false, length = 20)
    private String phone;
    @Column(nullable = false, length = 100)
    private String email;

    @CreationTimestamp
    @Column(name = "create_dt")
    private LocalDateTime createDt; // 생성일시
    @UpdateTimestamp
    @Column(name = "update_dt")
    private LocalDateTime updateDt; // 수정일시

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();


    @Builder(buildMethodName = "mockMvcBuilderMember")
    private Member(Long memberId, String name, String nickname, String password, String phone, String email) {
        this.memberId = memberId;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

    public Member(String name, String nickname, String password, String phone, String email) {
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

    public void updateMember(final MemberParamDto paramDto){
        this.name = paramDto.getName();
        this.nickname = paramDto.getNickname();
        this.password = paramDto.getPassword();
        this.phone = paramDto.getPhone();
        this.email = paramDto.getEmail();
    }

    public static Member createMember(final MemberParamDto paramDto){
        return new Member(paramDto.getName(), paramDto.getNickname(), paramDto.getPassword(), paramDto.getPhone(), paramDto.getEmail());
    }

}
