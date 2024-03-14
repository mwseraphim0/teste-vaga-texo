package com.br.service.impl;

import com.br.entity.StudioEntity;
import com.br.model.response.studio.StudioResponseDTO;
import com.br.repository.StudioRepository;
import com.br.service.StudioService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class StudioServiceImpl implements StudioService {

    private final StudioRepository studioRepository;

    @Override
    public void saveAll(List<StudioEntity> listStudio) {
        studioRepository.saveAll(listStudio);
    }

    @Override
    public StudioResponseDTO convertEntityToDto(StudioEntity studio) {
        return StudioResponseDTO.builder()
                .id(studio.getId())
                .name(studio.getName())
                .build();
    }

    @Override
    public List<StudioEntity> findAllById(List<Long> listStudios) {
        return studioRepository.findAllById(listStudios);
    }

}
