package api.afrilangue.controllers;

import api.afrilangue.dto.request.ThemeRequest;
import api.afrilangue.models.Theme;
import api.afrilangue.serrvices.theme.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/theme")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService service;


    @PostMapping("/")
    public ResponseEntity<?> createOne(@RequestBody ThemeRequest themeRequest) {
        return ResponseEntity.ok(service.create(themeRequest));
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
                                       @RequestBody Theme theme) {
        return ResponseEntity.ok(service.update(id, theme));
    }

    @GetMapping("/language/{id}")
    public ResponseEntity<?> findByLanguage(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.findByLanguage(id));
    }
}
