package com.borad.domain.borard;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
public class board {
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
}
