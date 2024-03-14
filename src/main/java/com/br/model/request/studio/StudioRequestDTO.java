package com.br.model.request.studio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudioRequestDTO {

    private String name;
}
