package com.gym.infrastructure.persistence.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "training_types")
public class TrainingTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "training_type_name", nullable = false, unique = true)
    private String name;

    protected TrainingTypeEntity() {}

    public TrainingTypeEntity(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}