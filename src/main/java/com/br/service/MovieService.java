package com.br.service;

import com.br.entity.MovieEntity;
import com.br.model.request.movie.MovieRequestDTO;
import com.br.model.response.movie.MovieResponseDTO;

import java.util.List;

public interface MovieService {

    void save(MovieEntity movieEntity);
    
    void saveAll(List<MovieEntity> listMovieSave);

    List<MovieResponseDTO> findAllMovies();

    List<MovieResponseDTO> findMovieByTitle(String title);

    List<MovieResponseDTO> findMovieByYear(Integer year);

    List<MovieResponseDTO> findMovieByWinner();

    void deleteMovieById(Long id);

    MovieResponseDTO createMovie(MovieRequestDTO request);

    MovieResponseDTO updateMovie(Long id, MovieRequestDTO request);
}
