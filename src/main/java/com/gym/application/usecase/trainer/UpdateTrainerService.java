package com.gym.application.usecase.trainer;

import com.gym.application.exception.NotFoundException;
import com.gym.application.port.input.auth.AuthCredentials;
import com.gym.application.port.input.auth.AuthenticateUseCase;
import com.gym.application.port.input.trainer.update.UpdateTrainerUseCase;
import com.gym.application.port.output.TrainerRepository;
import com.gym.domain.Trainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateTrainerService implements UpdateTrainerUseCase {

    private final AuthenticateUseCase authenticator;
    private final TrainerRepository trainerRepository;

    public UpdateTrainerService(AuthenticateUseCase authenticator,
                                TrainerRepository trainerRepository) {
        this.authenticator = authenticator;
        this.trainerRepository = trainerRepository;
    }

    @Override
    @Transactional
    public Trainer updateTrainerProfile(AuthCredentials auth, Trainer updatedTrainer) {
        validate(auth, updatedTrainer);
        authenticator.authenticate(auth);

        Trainer existing = trainerRepository.findByUsername(auth.username())
                .orElseThrow(() -> new NotFoundException(
                        "Trainer not found: " + auth.username()));

        applyChanges(existing, updatedTrainer);
        return trainerRepository.save(existing);
    }

    private void applyChanges(Trainer target, Trainer source) {
        if (source.getFirstName() != null && !source.getFirstName().isBlank())
            target.setFirstName(source.getFirstName());
        if (source.getLastName() != null && !source.getLastName().isBlank())
            target.setLastName(source.getLastName());
        if (source.getSpecialization() != null)
            target.setSpecialization(source.getSpecialization());
        target.setActive(source.isActive());
    }

    private void validate(AuthCredentials auth, Trainer updated) {
        if (auth == null)
            throw new IllegalArgumentException("auth is required");
        if (updated == null)
            throw new IllegalArgumentException("updated is required");
    }
}