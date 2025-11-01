package com.fitnesstracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;   // e.g., Running, Cycling
    private int duration;  // in minutes
    private int steps;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String notes;

    // ✅ Link workout to user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore // avoids recursion when returning JSON
    private User user;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public int getSteps() { return steps; }
    public void setSteps(int steps) { this.steps = steps; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
