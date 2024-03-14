package com.br.repository;

import com.br.entity.MovieEntity;
import com.br.entity.MovieProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieProducerRepository extends JpaRepository<MovieProducerEntity, Long> {

    void deleteByMovie(MovieEntity movieEntity);
}
