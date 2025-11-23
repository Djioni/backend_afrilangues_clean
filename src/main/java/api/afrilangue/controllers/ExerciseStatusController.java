package api.afrilangue.controllers;

import api.afrilangue.dto.request.*;
import api.afrilangue.models.ExerciseStatus;
import api.afrilangue.serrvices.exercisestatus.ExerciseStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static api.afrilangue.helper.ResponseUtils.apiErrorResponse;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/status")
@RequiredArgsConstructor
public class ExerciseStatusController {
    private final ExerciseStatusService service;

    @PostMapping("/")
    public ResponseEntity<?> createOne(@RequestBody ExerciseStatusRequest exercisesRequest) {
        try {
            return ResponseEntity.ok(service.create(exercisesRequest));
        } catch (Exception e) {
            return apiErrorResponse(HttpStatus.BAD_REQUEST, e);
        }
    }

    @GetMapping("/")
    public List<ExerciseStatus> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(service.find(id));
        } catch (Exception e) {
            return apiErrorResponse(HttpStatus.BAD_REQUEST, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(service.delete(id));
        } catch (Exception e) {
            return apiErrorResponse(HttpStatus.BAD_REQUEST, e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOne(@PathVariable("id") String id,
                                     @RequestBody ExerciseStatus exercise) {
        try {
            return ResponseEntity.ok(service.update(id, exercise));
        } catch (Exception e) {
            return apiErrorResponse(HttpStatus.BAD_REQUEST, e);
        }
    }

    @PostMapping("/exercise")
    public ResponseEntity<?> findExerciseStatusByUser(@RequestBody ExerciseStatusByExerciseRequest exercisesRequest) {
        try {
            return ResponseEntity.ok(service.findExerciseStatusByUser(exercisesRequest.getUserId(), exercisesRequest.getExerciseId()));
        } catch (Exception e) {
            return apiErrorResponse(HttpStatus.BAD_REQUEST, e);
        }
    }

    @PostMapping("/lesson/section")
    public ResponseEntity<?> findLessonSectionStatusByUser(@RequestBody LessonSectionStatusByExerciseRequest exercisesRequest) {
        try {
            return ResponseEntity.ok(service.findLessonSectionStatusByUser(exercisesRequest.getUserId(), exercisesRequest.getLessonSectionId()));
        } catch (Exception e) {
            return apiErrorResponse(HttpStatus.BAD_REQUEST, e);
        }
    }

    @PostMapping("/lesson")
    public ResponseEntity<?> findLessonStatusByUser(@RequestBody LessonStatusByExerciseRequest exercisesRequest) {
        try {
            return ResponseEntity.ok(service.findLessonByUser(exercisesRequest.getUserId(), exercisesRequest.getLessonId()));
        } catch (Exception e) {
            return apiErrorResponse(HttpStatus.BAD_REQUEST, e);
        }
    }

    @PostMapping("/theme")
    public ResponseEntity<?> find(@RequestBody ThemeSectionStatusByExerciseRequest exercisesRequest) {
        try {
            return ResponseEntity.ok(service.findThemeByUser(exercisesRequest.getUserId(), exercisesRequest.getThemeId()));
        } catch (Exception e) {
            return apiErrorResponse(HttpStatus.BAD_REQUEST, e);
        }
    }
}
