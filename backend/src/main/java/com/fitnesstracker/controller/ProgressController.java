package com.fitnesstracker.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitnesstracker.model.Workout;
import com.fitnesstracker.repository.WorkoutRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class ProgressController {

    @Autowired
    private WorkoutRepository workoutRepository;

    // Get monthly progress for a user
    @GetMapping("/progress")
    public ResponseEntity<?> getProgress(@RequestParam Long userId) {
        List<Workout> workouts = workoutRepository.findByUserId(userId);

        Map<String, Object> progress = new HashMap<>();

        // Total workouts
        progress.put("totalWorkouts", workouts.size());

        // Total duration
        int totalDuration = workouts.stream().mapToInt(Workout::getDuration).sum();
        progress.put("totalDuration", totalDuration);

        // Total steps
        int totalSteps = workouts.stream().mapToInt(Workout::getSteps).sum();
        progress.put("totalSteps", totalSteps);

        // Monthly progress
        Map<String, Long> monthlyCounts = workouts.stream()
            .collect(Collectors.groupingBy(
                w -> w.getDate().getMonth().toString(),
                Collectors.counting()
            ));
        progress.put("monthlyWorkouts", monthlyCounts);

        return ResponseEntity.ok(progress);
    }
}
