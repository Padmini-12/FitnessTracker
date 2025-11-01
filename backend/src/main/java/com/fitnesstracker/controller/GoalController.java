package com.fitnesstracker.controller;

import com.fitnesstracker.model.Goal;
import com.fitnesstracker.model.User;
import com.fitnesstracker.repository.GoalRepository;
import com.fitnesstracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = "http://localhost:5173")
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Add a new goal
    @PostMapping
    public ResponseEntity<?> addGoal(@RequestBody Goal goal, @RequestParam Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        goal.setUser(user);
        return ResponseEntity.ok(goalRepository.save(goal));
    }

    // ✅ Get goals by userId
    @GetMapping
    public ResponseEntity<List<Goal>> getUserGoals(@RequestParam Long userId) {
        return ResponseEntity.ok(goalRepository.findByUserId(userId));
    }

    // ✅ Update a goal
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGoal(@PathVariable Long id, @RequestBody Goal updatedGoal) {
        return goalRepository.findById(id).map(goal -> {
            goal.setTitle(updatedGoal.getTitle());
            goal.setNotes(updatedGoal.getNotes());
            goal.setTargetDate(updatedGoal.getTargetDate());
            return ResponseEntity.ok(goalRepository.save(goal));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete goal
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoal(@PathVariable Long id) {
        if (goalRepository.existsById(id)) {
            goalRepository.deleteById(id);
            return ResponseEntity.ok("Goal deleted");
        }
        return ResponseEntity.notFound().build();
    }
}
