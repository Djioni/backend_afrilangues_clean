package api.afrilangue.controllers;

import api.afrilangue.dto.request.ExercisesRequest;
import api.afrilangue.models.Exercise;
import api.afrilangue.serrvices.exercice.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/exercise")
@RequiredArgsConstructor
public class ExerciceController {
    private final ExerciseService service;

    @PostMapping("/")
    public ResponseEntity<?> createOne(@RequestBody ExercisesRequest exercisesRequest) {
        return ResponseEntity.ok(service.create(exercisesRequest));
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.find(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOne(@PathVariable("id") String id,
                                       @RequestBody Exercise exercise) {
        return ResponseEntity.ok(service.update(id, exercise));
    }

    @GetMapping("/lessonsection/{id}")
    public ResponseEntity<?> findByTheme(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.findByLessonSection(id));
    }
}
