package com.br.repository;

import com.br.entity.MovieEntity;
import com.br.entity.MovieStudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieStudioRepository extends JpaRepository<MovieStudioEntity, Long> {

    void deleteByMovie(MovieEntity movieEntity);
}
