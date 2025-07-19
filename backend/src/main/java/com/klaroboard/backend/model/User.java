package com.klaroboard.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity // Tells JPA this class represents a table in the database
@Table(name = "users") // Specifies the actual table name
@Getter // Lombok annotation to automatically create getter methods
@Setter // Lombok annotation to automatically create setter methods
public class User {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the ID
    private Long id;

    @Column(nullable = false) // Database column cannot be null
    private String name;

    @Column(nullable = false, unique = true) // Column must be unique
    private String email;
}