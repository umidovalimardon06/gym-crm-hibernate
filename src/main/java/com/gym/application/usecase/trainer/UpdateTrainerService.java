package com.gym.application.usecase.trainer;

import com.gym.application.exception.NotFoundException;
import com.gym.application.port.input.trainer.update.UpdateTrainerUseCase;
import com.gym.application.port.output.TrainerRepository;
import com.gym.domain.Trainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateTrainerService implements UpdateTrainerUseCase {

    private final TrainerRepository trainerRepository;

    public UpdateTrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    @Transactional
    public Trainer updateTrainerProfile(String username, Trainer updatedTrainer) {
        validate(username, updatedTrainer);

        Trainer existing = trainerRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Trainer not found: " + username));

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

    private void validate(String username, Trainer updated) {
        if (username == null || username.isBlank())
            throw new IllegalArgumentException("username is required");
        if (updated == null)
            throw new IllegalArgumentException("updated is required");
    }
}