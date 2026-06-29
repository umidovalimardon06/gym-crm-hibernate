package com.gym.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "trainings")
public class TrainingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trainee_id", nullable = false)
    private TraineeEntity trainee;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trainer_id", nullable = false)
    private TrainerEntity trainer;

    @Column(name = "training_name", nullable = false)
    private String trainingName;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "training_type_id", nullable = false)
    private TrainingTypeEntity trainingType;

    @Column(name = "training_date", nullable = false)
    private LocalDate trainingDate;

    @Column(name = "training_duration", nullable = false)
    private Integer trainingDuration;

    protected TrainingEntity() {}

    public TrainingEntity(TraineeEntity trainee, TrainerEntity trainer, String trainingName,
                          TrainingTypeEntity trainingType, LocalDate trainingDate,
                          Integer trainingDuration) {
        this.trainee = trainee;
        this.trainer = trainer;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
    }

    public Long getId() { return id; }
    public TraineeEntity getTrainee() { return trainee; }
    public TrainerEntity getTrainer() { return trainer; }
    public String getTrainingName() { return trainingName; }
    public TrainingTypeEntity getTrainingType() { return trainingType; }
    public LocalDate getTrainingDate() { return trainingDate; }
    public Integer getTrainingDuration() { return trainingDuration; }
}