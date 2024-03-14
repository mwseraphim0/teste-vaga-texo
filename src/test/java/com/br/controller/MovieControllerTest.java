package com.br.controller;

import com.br.model.request.movie.MovieRequestDTO;
import com.br.model.response.movie.MovieResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void successCreateMovie() throws Exception {
        MovieRequestDTO request = new MovieRequestDTO();
        request.setYear(2023);
        request.setTitle("Suzume");
        request.setWinner(true);
        request.setListStudios(List.of(1L, 2L, 3L));
        request.setListProducers(List.of(1L));

        mockMvc.perform(post("/api/movies")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void errorCreateMovie() throws Exception {
        mockMvc.perform(post("/api/movies")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(new MovieRequestDTO())))
                .andExpect(jsonPath("status", is(400)));
    }

    @Test
    void findAllMovies() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/movies"))
                .andReturn();

        List<MovieResponseDTO> listMovies = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<MovieResponseDTO>>() {
        });

        assertFalse(listMovies.isEmpty());
    }

    @Test
    void findByYearExisting() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/movies/year/{year}", 1981))
                .andReturn();

        List<MovieResponseDTO> listMovies = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<MovieResponseDTO>>() {
        });

        assertFalse(listMovies.isEmpty());
    }

    @Test
    void findByYearNotExisting() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/movies/year/{year}", 2999))
                .andReturn();

        List<MovieResponseDTO> listMovies = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<MovieResponseDTO>>() {
        });

        assertTrue(listMovies.isEmpty());
    }

}