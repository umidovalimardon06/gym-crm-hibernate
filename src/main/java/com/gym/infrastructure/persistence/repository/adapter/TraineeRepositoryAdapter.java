package com.gym.infrastructure.persistence.repository.adapter;

import com.gym.application.port.output.TraineeRepository;
import com.gym.domain.Trainee;
import com.gym.infrastructure.persistence.mapper.TraineeMapper;
import com.gym.infrastructure.persistence.repository.jpa.TraineeJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class TraineeRepositoryAdapter implements TraineeRepository {

    private final TraineeJpaRepository jpa;
    private final TraineeMapper mapper;

    public TraineeRepositoryAdapter(TraineeJpaRepository jpa, TraineeMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Trainee save(Trainee trainee) {
        return mapper.toDomain(jpa.save(mapper.toEntity(trainee)));
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        return jpa.findByUser_Username(username).map(mapper::toDomain);
    }

    @Override
    public List<Trainee> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        jpa.deleteByUser_Username(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpa.existsByUser_Username(username);
    }
}