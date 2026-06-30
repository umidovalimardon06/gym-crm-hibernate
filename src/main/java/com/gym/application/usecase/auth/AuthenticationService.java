package com.gym.application.usecase.auth;

import com.gym.application.exception.AuthenticationException;
import com.gym.application.port.input.auth.AuthCredentials;
import com.gym.application.port.input.auth.AuthenticateUseCase;
import com.gym.application.port.output.TraineeRepository;
import com.gym.application.port.output.TrainerRepository;
import com.gym.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements AuthenticateUseCase {

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;

    public AuthenticationService(TraineeRepository traineeRepository,
                                 TrainerRepository trainerRepository) {
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
    }

    @Override
    public void authenticate(AuthCredentials credentials) {
        if (credentials == null
                || credentials.username() == null
                || credentials.password() == null) {
            throw new AuthenticationException("Invalid credentials");
        }

        Optional<? extends User> user = traineeRepository
                .findByUsername(credentials.username())
                .map(t -> (User) t)
                .or(() -> trainerRepository.findByUsername(credentials.username()).map(t -> (User) t));

        User found = user.orElseThrow(() -> new AuthenticationException("Invalid credentials"));

        if (!found.getPassword().equals(credentials.password())) throw new AuthenticationException("Invalid credentials");
        if (!found.isActive()) throw new AuthenticationException("Account is inactive");
    }
}