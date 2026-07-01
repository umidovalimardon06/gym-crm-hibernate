package com.gym.application.usecase.trainee;

import com.gym.application.exception.InvalidStateException;
import com.gym.application.exception.NotFoundException;
import com.gym.application.port.input.auth.AuthCredentials;
import com.gym.application.port.input.auth.AuthenticateUseCase;
import com.gym.application.port.input.trainer.update.ChangeTrainerStatusUseCase;
import com.gym.application.port.output.TraineeRepository;
import com.gym.domain.Trainee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeTraineeStatusService implements ChangeTrainerStatusUseCase {

    private final AuthenticateUseCase authenticator;
    private final TraineeRepository traineeRepository;

    public ChangeTraineeStatusService(AuthenticateUseCase authenticator,
                                      TraineeRepository traineeRepository) {
        this.authenticator = authenticator;
        this.traineeRepository = traineeRepository;
    }

    @Override
    @Transactional
    public void activate(AuthCredentials auth) {
        setStatus(auth, true);
    }

    @Override
    @Transactional
    public void deactivate(AuthCredentials auth) {
        setStatus(auth, false);
    }

    private void setStatus(AuthCredentials auth, boolean desired) {
        if (auth == null)
            throw new IllegalArgumentException("auth is required");
        authenticator.verifyCredentials(auth);

        Trainee trainee = traineeRepository.findByUsername(auth.username())
                .orElseThrow(() -> new NotFoundException(
                        "Trainee not found: " + auth.username()));

        if (trainee.isActive() == desired) {
            throw new InvalidStateException(
                    "Trainee is already " + (desired ? "active" : "inactive"));
        }

        trainee.setActive(desired);
        traineeRepository.save(trainee);
    }
}