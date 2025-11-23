package api.afrilangue.controllers;

import api.afrilangue.dto.request.LessonRequest;
import api.afrilangue.models.Lesson;
import api.afrilangue.serrvices.lesson.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService service;

    @PostMapping("/")
    public ResponseEntity<?> createOne(@RequestBody LessonRequest lessonRequest) {
        return ResponseEntity.ok(service.create(lessonRequest));
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
                                       @RequestBody Lesson lesson) {
        return ResponseEntity.ok(service.update(id, lesson));
    }

    @GetMapping("/theme/{id}")
    public ResponseEntity<?> findByTheme(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.findByTheme(id));
    }

}
