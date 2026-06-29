package com.gym.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trainers")
public class TrainerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "specialization_id", nullable = false)
    private TrainingTypeEntity specialization;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @ManyToMany(mappedBy = "trainers", fetch = FetchType.LAZY)
    private Set<TraineeEntity> trainees = new HashSet<>();

    protected TrainerEntity() {}

    public TrainerEntity(TrainingTypeEntity specialization, UserEntity user) {
        this.specialization = specialization;
        this.user = user;
    }

    public Long getId() { return id; }
    public TrainingTypeEntity getSpecialization() { return specialization; }
    public UserEntity getUser() { return user; }
    public Set<TraineeEntity> getTrainees() { return trainees; }

    public void setSpecialization(TrainingTypeEntity specialization) { this.specialization = specialization; }
    public void setUser(UserEntity user) { this.user = user; }
}