package com.br.model.request.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieRequestDTO {

    @NotNull(message = "Year may not be null!")
    private Integer year;

    @NotEmpty(message = "Title may not be empty!")
    @NotNull(message = "Title may not be null!")
    @NotBlank(message = "The title must be informed!")
    private String title;

    private boolean winner;

    @NotEmpty(message = "List of studio cannot be empty!")
    @NotNull(message = "List of studio cannot be null!")
    private List<Long> listStudios;

    @NotEmpty(message = "List of producer cannot be empty!")
    @NotNull(message = "List of producer cannot be null!")
    private List<Long> listProducers;
}
