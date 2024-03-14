package com.br.service;

import com.br.entity.ProducerEntity;
import com.br.model.response.producer.ProducerResponseDTO;

import java.util.List;

public interface ProducerService {
    void saveAll(List<ProducerEntity> listProducer);

    ProducerResponseDTO convertEntityToDto(ProducerEntity producer);

    List<ProducerEntity> findAllById(List<Long> listProducers);

}
