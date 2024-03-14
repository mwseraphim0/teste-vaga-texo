package com.br.model.response.movie;

import com.br.model.response.producer.ProducerResponseDTO;
import com.br.model.response.studio.StudioResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResponseDTO {

    private Long id;
    private Integer year;
    private String title;
    private Boolean winner;
    private List<StudioResponseDTO> listStudios;
    private List<ProducerResponseDTO> listProducers;
}
