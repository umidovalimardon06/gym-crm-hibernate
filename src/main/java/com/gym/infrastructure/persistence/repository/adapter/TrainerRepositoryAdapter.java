package com.gym.infrastructure.persistence.repository.adapter;

import com.gym.application.port.output.TrainerRepository;
import com.gym.domain.Trainer;
import com.gym.infrastructure.persistence.mapper.TrainerMapper;
import com.gym.infrastructure.persistence.repository.jpa.TrainerJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainerRepositoryAdapter implements TrainerRepository {

    private final TrainerJpaRepository jpa;
    private final TrainerMapper mapper;

    public TrainerRepositoryAdapter(TrainerJpaRepository jpa, TrainerMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Trainer save(Trainer trainer) {
        return mapper.toDomain(jpa.save(mapper.toEntity(trainer)));
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        return jpa.findByUser_Username(username).map(mapper::toDomain);
    }

    @Override
    public List<Trainer> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Trainer> findTrainersNotAssignedToTrainee(String traineeUsername) {
        return jpa.findTrainersNotAssignedToTrainee(traineeUsername)
                .stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpa.existsByUser_Username(username);
    }
}