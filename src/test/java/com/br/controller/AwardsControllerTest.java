package com.br.controller;

import com.br.model.response.award.AwardRangeResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class AwardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void findAllMovies() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/awards")).andReturn();

        AwardRangeResponseDto awardRangeResponseDto = mapper.readValue(mvcResult.getResponse().getContentAsString(), AwardRangeResponseDto.class);

        assertFalse(awardRangeResponseDto.getMax().isEmpty());
        assertFalse(awardRangeResponseDto.getMin().isEmpty());
    }

}