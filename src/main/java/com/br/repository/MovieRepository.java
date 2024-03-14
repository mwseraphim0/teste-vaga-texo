package com.br.repository;

import com.br.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    List<MovieEntity> findAllByTitleContainingIgnoreCase(String title);

    List<MovieEntity> findByYear(Integer year);

    List<MovieEntity> findByWinner(boolean winner);
}
