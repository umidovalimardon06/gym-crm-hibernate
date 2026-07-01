package com.gym.application.port.input.trainee.update;

import com.gym.domain.Trainee;

public interface UpdateTraineeUseCase {
    Trainee updateTraineeProfile(String username, Trainee updated);
}
