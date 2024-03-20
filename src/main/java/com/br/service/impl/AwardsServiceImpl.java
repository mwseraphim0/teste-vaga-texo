package com.br.service.impl;

import com.br.model.response.award.AwardRangeResponseDto;
import com.br.model.response.award.AwardsResponseDto;
import com.br.model.response.movie.MovieResponseDTO;
import com.br.repository.ProducerRepository;
import com.br.service.AwardsService;
import com.br.service.MovieService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class AwardsServiceImpl implements AwardsService {

    private final MovieService movieService;

    @Override
    public AwardRangeResponseDto findAwardRange() {
        List<MovieResponseDTO> listMovies = movieService.findMovieByWinner();

        List<MovieResponseDTO> listMoviesWinner = listMovies.stream()
                .filter(MovieResponseDTO::getWinner)
                .toList();

        List<String> producers = new ArrayList<>();
        listMoviesWinner.forEach(movie -> movie.getListProducers().forEach(producer -> producers.add(producer.getName())));
        List<String> listProducerWinner = producers.stream()
                .collect(Collectors.groupingBy(nome -> nome, Collectors.counting()))
                .entrySet().stream()
                .filter(entryValidation -> entryValidation.getValue() > 1)
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .toList();


        if (listProducerWinner.isEmpty()) {
            return AwardRangeResponseDto.builder()
                    .max(new ArrayList<>())
                    .min(new ArrayList<>())
                    .build();
        }

        Map<String, List<MovieResponseDTO>> mapProduceMovie = new HashMap<>();
        listMoviesWinner.stream()
                .filter(movie -> movie.getListProducers().stream().anyMatch(producer -> listProducerWinner.contains(producer.getName())))
                .sorted(Comparator.comparing(MovieResponseDTO::getYear))
                .forEach(movie -> movie.getListProducers().forEach(producer -> {
                    List<MovieResponseDTO> listMovie = new ArrayList<>();
                    if (mapProduceMovie.containsKey(producer.getName())) {
                        listMovie = mapProduceMovie.get(producer.getName());
                    }
                    listMovie.add(movie);
                    mapProduceMovie.put(producer.getName(), listMovie);
                }));

        List<AwardsResponseDto> listAwards = new ArrayList<>();
        mapProduceMovie.entrySet().stream()
                .filter(producerMovieFilter -> producerMovieFilter.getValue().size() > 1)
                .forEach(producerMovieMap -> {
                    String producerName = producerMovieMap.getKey();

                    List<MovieResponseDTO> listMoviesCount = producerMovieMap.getValue().stream()
                            .sorted(Comparator.comparing(MovieResponseDTO::getYear))
                            .toList();

                    listMoviesCount.stream()
                            .reduce((first, second) -> {
                                AwardsResponseDto build = AwardsResponseDto.builder()
                                        .producer(producerName)
                                        .interval(second.getYear() - first.getYear())
                                        .previousWin(first.getYear())
                                        .followingWin(second.getYear())
                                        .build();
                                listAwards.add(build);
                                return second;
                            });
                });

        if (listAwards.isEmpty()) {
            return AwardRangeResponseDto.builder()
                    .max(new ArrayList<>())
                    .min(new ArrayList<>())
                    .build();
        }

        AwardsResponseDto maxProducerAwards = listAwards.stream().max(Comparator.comparing(AwardsResponseDto::getInterval)).get();
        AwardsResponseDto minProducerAwards = listAwards.stream().min(Comparator.comparing(AwardsResponseDto::getInterval)).get();

        return AwardRangeResponseDto.builder()
                .min(listAwards.stream().filter(awards -> Objects.equals(minProducerAwards.getInterval(), awards.getInterval())).toList())
                .max(listAwards.stream().filter(awards -> Objects.equals(maxProducerAwards.getInterval(), awards.getInterval())).toList())
                .build();

    }
}
