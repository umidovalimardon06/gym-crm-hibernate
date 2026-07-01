package com.gym.application.usecase.trainee;

import com.gym.application.exception.NotFoundException;
import com.gym.application.port.input.trainee.update.UpdateTraineeUseCase;
import com.gym.application.port.output.TraineeRepository;
import com.gym.domain.Trainee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateTraineeService implements UpdateTraineeUseCase {

    private final TraineeRepository traineeRepository;

    public UpdateTraineeService(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
    }

    @Override
    @Transactional
    public Trainee updateTraineeProfile(String username, Trainee updated) {
        validate(username, updated);

        Trainee existing = traineeRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Trainee not found: " + username));

        applyChanges(existing, updated);
        return traineeRepository.save(existing);
    }

    private void applyChanges(Trainee target, Trainee source) {
        if (source.getFirstName() != null && !source.getFirstName().isBlank())
            target.setFirstName(source.getFirstName());
        if (source.getLastName() != null && !source.getLastName().isBlank())
            target.setLastName(source.getLastName());
        if (source.getDateOfBirth() != null)
            target.setDateOfBirth(source.getDateOfBirth());
        if (source.getAddress() != null)
            target.setAddress(source.getAddress());
        target.setActive(source.isActive());
    }

    private void validate(String username, Trainee updated) {
        if (username == null || username.isBlank())
            throw new IllegalArgumentException("username is required");
        if (updated == null)
            throw new IllegalArgumentException("updated is required");
    }
}