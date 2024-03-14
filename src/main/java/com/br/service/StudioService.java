package com.br.service;

import com.br.entity.StudioEntity;
import com.br.model.response.studio.StudioResponseDTO;

import java.util.List;

public interface StudioService {
    void saveAll(List<StudioEntity> listStudio);

    StudioResponseDTO convertEntityToDto(StudioEntity studio);

    List<StudioEntity> findAllById(List<Long> listStudios);
}
