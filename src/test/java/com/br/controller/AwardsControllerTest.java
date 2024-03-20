package com.br.controller;

import com.br.model.response.award.AwardRangeResponseDto;
import com.br.model.response.award.AwardsResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        assertEquals(1, awardRangeResponseDto.getMax().size(),"Deveria existir somente 1 produtor ao ganhar o premio no menor periodo");
        assertEquals(1, awardRangeResponseDto.getMin().size(), "Deveria existir somente 1 produtor ao ganhar o premio no maior periodo");

        AwardsResponseDto maxAwardsResponseDto = awardRangeResponseDto.getMax().get(0);
        assertEquals("Matthew Vaughn", maxAwardsResponseDto.getProducer());
        assertEquals(13, maxAwardsResponseDto.getInterval());
        assertEquals(2002, maxAwardsResponseDto.getPreviousWin());
        assertEquals(2015, maxAwardsResponseDto.getFollowingWin());

        AwardsResponseDto minAwardsResponseDto = awardRangeResponseDto.getMin().get(0);
        assertEquals("Joel Silver", minAwardsResponseDto.getProducer());
        assertEquals(1, minAwardsResponseDto.getInterval());
        assertEquals(1990, minAwardsResponseDto.getPreviousWin());
        assertEquals(1991, minAwardsResponseDto.getFollowingWin());
    }

}