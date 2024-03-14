package com.br.service.impl;

import com.br.entity.MovieEntity;
import com.br.entity.MovieProducerEntity;
import com.br.entity.MovieStudioEntity;
import com.br.entity.ProducerEntity;
import com.br.entity.StudioEntity;
import com.br.exception.ResourceNotFoundException;
import com.br.model.request.movie.MovieRequestDTO;
import com.br.model.response.movie.MovieResponseDTO;
import com.br.model.response.producer.ProducerResponseDTO;
import com.br.model.response.studio.StudioResponseDTO;
import com.br.repository.MovieRepository;
import com.br.service.MovieProducerService;
import com.br.service.MovieService;
import com.br.service.MovieStudioService;
import com.br.service.ProducerService;
import com.br.service.StudioService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final StudioService studioService;

    private final ProducerService producerService;

    private final MovieProducerService movieProducerService;

    private final MovieStudioService movieStudioService;

    @Override
    public void saveAll(List<MovieEntity> listMovieSave) {
        movieRepository.saveAllAndFlush(listMovieSave);
    }

    @Override
    public void save(MovieEntity movieEntity) {
        movieRepository.saveAndFlush(movieEntity);
    }

    @Override
    public List<MovieResponseDTO> findAllMovies() {
        List<MovieEntity> listMovies = movieRepository.findAll();
        return listMovies.stream().map(this::convertEntityToDto).toList();
    }

    @Override
    public List<MovieResponseDTO> findMovieByTitle(String title) {
        List<MovieEntity> listMovies = movieRepository.findAllByTitleContainingIgnoreCase(title);
        return listMovies.stream().map(this::convertEntityToDto).toList();
    }

    @Override
    public List<MovieResponseDTO> findMovieByYear(Integer year) {
        List<MovieEntity> listMovies = movieRepository.findByYear(year);
        return listMovies.stream().map(this::convertEntityToDto).toList();
    }

    @Override
    public List<MovieResponseDTO> findMovieByWinner() {
        List<MovieEntity> listMovies = movieRepository.findByWinner(true);
        return listMovies.stream().map(this::convertEntityToDto).toList();
    }


    @Override
    public void deleteMovieById(Long id) {
        Optional<MovieEntity> movieEntityOptional = movieRepository.findById(id);

        if (movieEntityOptional.isEmpty()) {
            throw new ResourceNotFoundException("Movie not found!");
        }

        movieRepository.deleteById(id);
    }

    @Override
    public MovieResponseDTO createMovie(MovieRequestDTO request) {
        List<StudioEntity> listStudioEntity = findListStudioEntity(request.getListStudios());
        List<ProducerEntity> listProducerEntity = findListProducerEntity(request.getListProducers());
        MovieEntity entity = new MovieEntity();

        List<MovieStudioEntity> listMovieEntity = generateListMovieStudio(listStudioEntity, entity);
        List<MovieProducerEntity> listMovieProducer = generateListMovieProducer(listProducerEntity, entity);

        entity.setTitle(request.getTitle());
        entity.setYear(request.getYear());
        entity.setWinner(request.isWinner());
        entity.setListStudios(listMovieEntity);
        entity.setListProducers(listMovieProducer);
        movieRepository.saveAndFlush(entity);

        return convertEntityToDto(entity);
    }

    private static List<MovieProducerEntity> generateListMovieProducer(List<ProducerEntity> listProducerEntity, MovieEntity entity) {
        return listProducerEntity.stream()
                .map(producerEntity -> {
                    MovieProducerEntity movieProducerEntity = new MovieProducerEntity();
                    movieProducerEntity.setMovie(entity);
                    movieProducerEntity.setProducer(producerEntity);
                    return movieProducerEntity;
                }).toList();
    }

    private List<ProducerEntity> findListProducerEntity(List<Long> listProducer) {
        List<ProducerEntity> listProducerEntity = producerService.findAllById(listProducer);
        if (listProducerEntity.isEmpty()) {
            throw new ResourceNotFoundException("Producer not found!");
        }
        return listProducerEntity;
    }

    private List<StudioEntity> findListStudioEntity(List<Long> listStudio) {
        List<StudioEntity> listStudioEntity = studioService.findAllById(listStudio);
        if (listStudioEntity.isEmpty()) {
            throw new ResourceNotFoundException("Studio not found!");
        }
        return listStudioEntity;
    }

    private static List<MovieStudioEntity> generateListMovieStudio(List<StudioEntity> listStudioEntity, MovieEntity entity) {
        return listStudioEntity.stream()
                .map(studioEntity -> {
                    MovieStudioEntity movieStudioEntity = new MovieStudioEntity();
                    movieStudioEntity.setMovie(entity);
                    movieStudioEntity.setStudio(studioEntity);
                    return movieStudioEntity;
                }).toList();
    }

    @Override
    @Transactional
    public MovieResponseDTO updateMovie(Long id, MovieRequestDTO request) {
        Optional<MovieEntity> movieEntityOptional = movieRepository.findById(id);

        if (movieEntityOptional.isEmpty()) {
            throw new ResourceNotFoundException("Movie not found!");
        }

        MovieEntity movieEntity = movieEntityOptional.get();
        boolean updateStudio = movieEntity.getListStudios().stream()
                .map(movieEstudioEntityMap -> movieEstudioEntityMap.getStudio().getId())
                .noneMatch(request.getListStudios()::contains);

        if (updateStudio) {
            movieStudioService.deleteByMovieEntity(movieEntity);
            movieEntity.setListStudios(new ArrayList<>());
            List<StudioEntity> listStudioEntity = findListStudioEntity(request.getListStudios());
            List<MovieStudioEntity> movieStudioEntities = generateListMovieStudio(listStudioEntity, movieEntity);
            movieEntity.getListStudios().addAll(movieStudioEntities);
        }

        boolean updateProducer = movieEntity.getListProducers().stream()
                .map(movieProducerEntityMap -> movieProducerEntityMap.getProducer().getId())
                .noneMatch(request.getListProducers()::contains);

        if (updateProducer) {
            movieProducerService.deleteByMovieEntity(movieEntity);
            movieEntity.setListProducers(new ArrayList<>());
            List<ProducerEntity> listProducerEntity = findListProducerEntity(request.getListProducers());
            List<MovieProducerEntity> movieProducerEntities = generateListMovieProducer(listProducerEntity, movieEntity);
            movieEntity.getListProducers().addAll(movieProducerEntities);
        }

        movieEntity.setTitle(request.getTitle());
        movieEntity.setYear(request.getYear());
        movieEntity.setWinner(request.isWinner());

        movieRepository.saveAndFlush(movieEntity);
        return convertEntityToDto(movieEntity);
    }


    private MovieResponseDTO convertEntityToDto(MovieEntity movieEntity) {
        List<StudioResponseDTO> listStudioResponse = movieEntity.getListStudios().stream()
                .map(studio -> studioService.convertEntityToDto(studio.getStudio()))
                .toList();

        List<ProducerResponseDTO> listProducerResponse = movieEntity.getListProducers().stream()
                .map(producer -> producerService.convertEntityToDto(producer.getProducer()))
                .toList();

        return MovieResponseDTO.builder()
                .id(movieEntity.getId())
                .title(movieEntity.getTitle())
                .year(movieEntity.getYear())
                .winner(movieEntity.getWinner())
                .listStudios(listStudioResponse)
                .listProducers(listProducerResponse)
                .build();
    }

}
