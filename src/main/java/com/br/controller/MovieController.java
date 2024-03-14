package com.br.controller;

import com.br.model.request.movie.MovieRequestDTO;
import com.br.model.response.movie.MovieResponseDTO;
import com.br.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> findAllMovies() {
        return ResponseEntity.ok(movieService.findAllMovies());
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<MovieResponseDTO>> findMovieByTitle(@PathVariable String title) {
        return ResponseEntity.ok(movieService.findMovieByTitle(title));
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<MovieResponseDTO>> findMovieByYear(@PathVariable Integer year) {
        return ResponseEntity.ok(movieService.findMovieByYear(year));
    }

    @GetMapping("/winner")
    public ResponseEntity<List<MovieResponseDTO>> findMovieByWinner() {
        return ResponseEntity.ok(movieService.findMovieByWinner());
    }

    @PostMapping
    public ResponseEntity<MovieResponseDTO> createMovie(@RequestBody @Validated MovieRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.createMovie(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> updateMovie(@PathVariable Long id, @RequestBody @Validated MovieRequestDTO request) {
        return ResponseEntity.ok(movieService.updateMovie(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMovieById(@PathVariable Long id) {
        movieService.deleteMovieById(id);
        return ResponseEntity.ok().build();
    }
}
