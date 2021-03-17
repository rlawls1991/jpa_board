package com.borad.domain.borard.repository;

import com.borad.domain.borard.Board;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
}
