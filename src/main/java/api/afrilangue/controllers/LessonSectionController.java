package api.afrilangue.controllers;

import api.afrilangue.dto.request.LessonSectionRequest;
import api.afrilangue.models.LessonSection;
import api.afrilangue.serrvices.lessonsection.LessonSectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/lessonsection")
@RequiredArgsConstructor
public class LessonSectionController {
    private final LessonSectionService service;

    @PostMapping("/")
    public ResponseEntity<?> createOne(@RequestBody LessonSectionRequest lessonSectionRequest) {
        return ResponseEntity.ok(service.create(lessonSectionRequest));
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
                                       @RequestBody LessonSection lessonSection) {

        return ResponseEntity.ok(service.update(id, lessonSection));
    }

    @GetMapping("/lesson/{id}")
    public ResponseEntity<?> findByLesson(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.findByLesson(id));
    }
}
