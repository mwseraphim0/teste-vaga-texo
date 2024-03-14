package com.br.repository;

import com.br.entity.StudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends JpaRepository<StudioEntity, Long> {

}
