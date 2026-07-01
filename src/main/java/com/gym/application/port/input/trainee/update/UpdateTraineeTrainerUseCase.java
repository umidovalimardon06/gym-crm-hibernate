package com.gym.application.port.input.trainee.update;

import com.gym.domain.Trainer;

import java.util.List;

public interface UpdateTraineeTrainerUseCase {
    List<Trainer> updateAssignedTrainers(String username, List<Trainer> trainers);
}
