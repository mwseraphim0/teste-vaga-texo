package com.br.entity;

import com.br.converter.BooleanToStringConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity(name = "MovieEntity")
@Table(name = "movie")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "\"year\"", nullable = false)
    private Integer year;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "winner", nullable = false)
    @Convert(converter = BooleanToStringConverter.class)
    private Boolean winner;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MovieStudioEntity> listStudios;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MovieProducerEntity> listProducers;
}
