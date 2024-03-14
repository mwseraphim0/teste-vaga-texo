package com.br.controller;

import com.br.model.response.award.AwardRangeResponseDto;
import com.br.service.AwardsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/awards")
public class AwardsController {

    private final AwardsService awardsService;

    @GetMapping
    public ResponseEntity<AwardRangeResponseDto> findAwardRange() {
        return ResponseEntity.ok(awardsService.findAwardRange());
    }

}
