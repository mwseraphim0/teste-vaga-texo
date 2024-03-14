package com.br.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieVO {

    private String year;
    private String title;
    private String studios;
    private String producers;
    private Boolean winner;
}
