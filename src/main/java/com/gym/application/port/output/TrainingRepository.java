package com.gym.application.port.output;

import com.gym.domain.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainingRepository {
    Training save(Training training);
    List<Training> findTraineeTrainings(String traineeUsername);
    List<Training> findTrainerTrainings(String trainerUsername);
}