package com.br.service.impl;

import com.br.entity.ProducerEntity;
import com.br.model.response.producer.ProducerResponseDTO;
import com.br.repository.ProducerRepository;
import com.br.service.ProducerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final ProducerRepository producerRepository;

    @Override
    public void saveAll(List<ProducerEntity> listProducer) {
        producerRepository.saveAllAndFlush(listProducer);
    }

    @Override
    public ProducerResponseDTO convertEntityToDto(ProducerEntity producer) {
        return ProducerResponseDTO.builder()
                .id(producer.getId())
                .name(producer.getName())
                .build();
    }

    @Override
    public List<ProducerEntity> findAllById(List<Long> listProducers) {
        return producerRepository.findAllById(listProducers);
    }
}
