package com.borad.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "member")
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;
    @Column(nullable = true, length = 20)
    private String name;
    @Column(nullable = true, length = 30)
    private String nickname;
    private String password;
    @Column(nullable = true, length = 20)
    private String phone;
    @Column(nullable = true, length = 100)
    private String email;
    @CreationTimestamp
    @Column(name = "create_dt")
    private Date createDt; // 생성일시
    @UpdateTimestamp
    @Column(name = "update_dt")
    private Date updateDt; // 수정일시

}
