package com.br.model.response.award;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AwardsResponseDto {

    private String producer;
    private Integer interval;
    private Integer previousWin;
    private String titlePreviousWinMovie;
    private Integer followingWin;
    private String titleFollowingWinMovie;
}
