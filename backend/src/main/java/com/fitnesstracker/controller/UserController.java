package com.fitnesstracker.controller;

import com.fitnesstracker.model.User;
import com.fitnesstracker.model.Workout;
import com.fitnesstracker.repository.UserRepository;
import com.fitnesstracker.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Add a workout for a specific user
    @PostMapping("/workouts")
    public ResponseEntity<?> addWorkout(@RequestBody Workout workout, @RequestParam Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            workout.setUser(user); // attach user
            Workout savedWorkout = workoutRepository.save(workout);
            return ResponseEntity.ok(savedWorkout);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving workout: " + e.getMessage());
        }
    }

    // ✅ Get workouts (all or by userId)
    @GetMapping("/workouts")
    public ResponseEntity<List<Workout>> getWorkouts(@RequestParam(required = false) Long userId) {
        List<Workout> workouts;
        if (userId != null) {
            workouts = workoutRepository.findByUserId(userId);
        } else {
            workouts = workoutRepository.findAll();
        }
        return ResponseEntity.ok(workouts);
    }

    // ✅ Delete workout by ID
    @DeleteMapping("/workouts/{id}")
    public ResponseEntity<String> deleteWorkout(@PathVariable Long id) {
        if (workoutRepository.existsById(id)) {
            workoutRepository.deleteById(id);
            return ResponseEntity.ok("Workout deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Workout not found");
        }
    }
}
