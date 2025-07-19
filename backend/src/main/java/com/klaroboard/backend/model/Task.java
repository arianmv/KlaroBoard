package com.klaroboard.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING) // Stores the enum as a string (e.g., "TO_DO")
    @Column(nullable = false)
    private TaskStatus status;

    // This creates the relationship: Many tasks can belong to one user.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // This creates a 'user_id' column in the 'tasks' table
    private User assignee;
}