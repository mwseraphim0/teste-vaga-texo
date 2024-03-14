package com.br.service.impl;

import com.br.entity.MovieEntity;
import com.br.repository.MovieStudioRepository;
import com.br.service.MovieStudioService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MovieStudioServiceImpl implements MovieStudioService {

    private final MovieStudioRepository movieStudioRepository;

    @Override
    public void deleteByMovieEntity(MovieEntity movieEntity) {
        movieStudioRepository.deleteByMovie(movieEntity);
    }
}
