package api.afrilangue.controllers;

import api.afrilangue.dto.request.LanguageRequest;
import api.afrilangue.dto.response.MemberByLanguage;
import api.afrilangue.models.Language;
import api.afrilangue.models.User;
import api.afrilangue.repositories.UserRepository;
import api.afrilangue.serrvices.language.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/language")
@RequiredArgsConstructor
public class LanguageController {
    private final LanguageService languageService;
    private final UserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity<?> createOne(@RequestBody LanguageRequest languageRequest) {
        return ResponseEntity.ok(languageService.create(languageRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Language>> findAll() {
        return ResponseEntity.ok(languageService.findAll());
    }

    @GetMapping("/")
    public ResponseEntity<List<Language>> findAllByUsername() {
        return ResponseEntity.ok(languageService.findAllByUsername());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable("id") String id) {
        return ResponseEntity.ok(languageService.find(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable("id") String id) {
        return ResponseEntity.ok(languageService.delete(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOne(@PathVariable("id") String id,
                                       @RequestBody Language language) {

        return ResponseEntity.ok(languageService.update(id, language));
    }

    @GetMapping("/member")
    public ResponseEntity<?> CountLanguageByUser() {
        return ResponseEntity.ok(calculateMember(userRepository.findAll(), languageService.findAll()));
    }

    public List<MemberByLanguage> calculateMember(List<User> users, List<Language> languages) {
        return languageService.getMemberByLanguages(users, languages);
    }


}
