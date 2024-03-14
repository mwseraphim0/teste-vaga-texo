package com.br.service.impl;

import com.br.entity.MovieEntity;
import com.br.repository.MovieProducerRepository;
import com.br.service.MovieProducerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MovieProducerServiceImpl implements MovieProducerService {

    private final MovieProducerRepository movieProducerRepository;

    @Override
    public void deleteByMovieEntity(MovieEntity movieEntity) {
        movieProducerRepository.deleteByMovie(movieEntity);
    }
}
