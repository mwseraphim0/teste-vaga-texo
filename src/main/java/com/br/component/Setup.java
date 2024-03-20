package com.br.component;

import com.br.entity.MovieEntity;
import com.br.entity.MovieProducerEntity;
import com.br.entity.MovieStudioEntity;
import com.br.entity.ProducerEntity;
import com.br.entity.StudioEntity;
import com.br.service.MovieService;
import com.br.service.ProducerService;
import com.br.service.StudioService;
import com.br.utils.StringUtils;
import com.br.vo.MovieVO;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Slf4j
@Component
@AllArgsConstructor
public class Setup {
    public static final String REGEX = ",| and ";

    private final MovieService movieService;

    private final StudioService studioService;

    private final ProducerService producerService;

    @PostConstruct
    private void setupData() {
        try (Reader reader = new FileReader(new ClassPathResource("movielist.csv").getFile()); BufferedReader bReader = new BufferedReader(reader)) {

            CSVFormat csvFormat = CSVFormat.Builder.create().setDelimiter(";").setHeader().setSkipHeaderRecord(true).setIgnoreHeaderCase(true).setTrim(true).build();

            // Conversão do arquivo csv
            CSVParser csvParser = new CSVParser(bReader, csvFormat);
            List<MovieVO> listMovieSave = getMovieVOS(csvParser);

            // Salvando studios
            List<StudioEntity> listSaveStudio = getListSaveStudio(listMovieSave);

            // Salvando produtores
            List<ProducerEntity> listSaveProducer = getListSaveProducer(listMovieSave);

            List<MovieEntity> listSaveEntity = listMovieSave.stream().map(movieVO -> convertMovieEntity(movieVO, listSaveStudio, listSaveProducer)).toList();
            movieService.saveAll(listSaveEntity);

        } catch (Exception ex) {
            log.error("Erron na impoartação dos dados -> ", ex.getMessage(), ex);
        }
    }

    private List<StudioEntity> getListSaveStudio(List<MovieVO> listMovieSave) {
        List<StudioEntity> listSaveStudio = new ArrayList<>();
        listMovieSave.stream()
                .map(movieVO -> convertStudioEntity(List.of(movieVO.getStudios().split(REGEX))))
                .forEach(listSaveStudio::addAll);

        listSaveStudio = listSaveStudio.stream()
                .filter(distinctByKey(StudioEntity::getName))
                .filter(studio -> StringUtils.validateString(studio.getName()))
                .toList();

        studioService.saveAll(listSaveStudio);
        return listSaveStudio;
    }

    private List<ProducerEntity> getListSaveProducer(List<MovieVO> listMovieSave) {
        List<ProducerEntity> listSaveProducer = new ArrayList<>();
        listMovieSave.stream().map(movieVO -> convertProducerEntity(List.of(movieVO.getProducers().split(REGEX)))).forEach(listSaveProducer::addAll);

        listSaveProducer = listSaveProducer.stream()
                .filter(distinctByKey(ProducerEntity::getName))
                .filter(producer -> StringUtils.validateString(producer.getName()))
                .toList();

        producerService.saveAll(listSaveProducer);
        return listSaveProducer;
    }

    private static List<MovieVO> getMovieVOS(CSVParser csvParser) {
        List<MovieVO> listMovieSave = new ArrayList<>();
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        csvRecords.forEach(movieRecord -> {
            MovieVO movieSave = MovieVO.builder()
                    .year(movieRecord.get("year"))
                    .title(movieRecord.get("title"))
                    .studios(movieRecord.get("studios"))
                    .producers(movieRecord.get("producers"))
                    .winner(movieRecord.get("winner").equalsIgnoreCase("yes"))
                    .build();
            listMovieSave.add(movieSave);
        });
        return listMovieSave;
    }

    public MovieEntity convertMovieEntity(MovieVO movie, List<StudioEntity> listStudios, List<ProducerEntity> listProducer) {
        MovieEntity movieEntity = new MovieEntity();
        List<String> listStudiosVo = Stream.of(movie.getStudios().split(REGEX)).map(String::trim).toList();

        List<MovieStudioEntity> listStudioEntity = listStudios.stream()
                .filter(studioEntity -> listStudiosVo.contains(studioEntity.getName()))
                .filter(studioValidation -> StringUtils.validateString(studioValidation.getName()))
                .map(studioEntity -> {
                    MovieStudioEntity studioEntitySave = new MovieStudioEntity();
                    studioEntitySave.setStudio(studioEntity);
                    studioEntitySave.setMovie(movieEntity);
                    return studioEntitySave;
                }).toList();

        List<String> listProducersVo = Stream.of(movie.getProducers().split(REGEX)).map(String::trim).toList();

        List<MovieProducerEntity> listProducerEntity = listProducer.stream()
                .filter(producerEntity -> listProducersVo.contains(producerEntity.getName()))
                .filter(producerValidation -> StringUtils.validateString(producerValidation.getName()))
                .map(producerEntity -> {
                    MovieProducerEntity movieProducerEntity = new MovieProducerEntity();
                    movieProducerEntity.setProducer(producerEntity);
                    movieProducerEntity.setMovie(movieEntity);
                    return movieProducerEntity;
                }).toList();


        movieEntity.setYear(StringUtils.convertStringToNumber(movie.getYear()));
        movieEntity.setTitle(movie.getTitle());
        movieEntity.setWinner(movie.getWinner());
        movieEntity.setListStudios(listStudioEntity);
        movieEntity.setListProducers(listProducerEntity);
        return movieEntity;

    }

    public List<StudioEntity> convertStudioEntity(List<String> listStudio) {
        return listStudio.stream().map(studio -> {
            StudioEntity entity = new StudioEntity();
            entity.setName(studio.trim());
            return entity;
        }).toList();
    }

    public List<ProducerEntity> convertProducerEntity(List<String> listProducer) {
        return listProducer.stream().map(producer -> {
            ProducerEntity entity = new ProducerEntity();
            entity.setName(producer.trim());
            return entity;
        }).toList();
    }


    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
