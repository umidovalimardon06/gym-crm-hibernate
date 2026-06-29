package com.gym.infrastructure.persistence.repository.jpa;

import com.gym.domain.TrainingType;
import com.gym.infrastructure.persistence.entity.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TrainingJpaRepository extends JpaRepository<TrainingEntity, Long> {

    @Query("""
           SELECT t FROM TrainingEntity t
           WHERE t.trainee.user.username = :username
             AND (:from IS NULL OR t.trainingDate >= :from)
             AND (:to   IS NULL OR t.trainingDate <= :to)
             AND (:trainerName IS NULL OR
                  LOWER(CONCAT(t.trainer.user.firstName, ' ', t.trainer.user.lastName))
                  LIKE LOWER(CONCAT('%', :trainerName, '%')))
             AND (:type IS NULL OR t.trainingType = :type)
           """)
    List<TrainingEntity> findTraineeTrainings(String username, LocalDate from, LocalDate to,
                                              String trainerName, TrainingType type);

    @Query("""
           SELECT t FROM TrainingEntity t
           WHERE t.trainer.user.username = :username
             AND (:from IS NULL OR t.trainingDate >= :from)
             AND (:to   IS NULL OR t.trainingDate <= :to)
             AND (:traineeName IS NULL OR
                  LOWER(CONCAT(t.trainee.user.firstName, ' ', t.trainee.user.lastName))
                  LIKE LOWER(CONCAT('%', :traineeName, '%')))
           """)
    List<TrainingEntity> findTrainerTrainings(String username, LocalDate from, LocalDate to,
                                              String traineeName);
}