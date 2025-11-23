package api.afrilangue.controllers;

import api.afrilangue.dto.request.UpdateUserRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.dto.response.UserResponse;
import api.afrilangue.models.User;
import api.afrilangue.serrvices.user.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserInfoService userInfoService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(userInfoService.getCurrentUser());
    }

    @PutMapping("/")
    public ResponseEntity<DefaultResponse> updateOne(@RequestBody UpdateUserRequest updateUserRequest) {

        return ResponseEntity.ok(userInfoService.update(updateUserRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse> deleteOne(@PathVariable("id") String id) {
        return ResponseEntity.ok(userInfoService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findOne(@PathVariable("id") String id) {
        return ResponseEntity.ok(userInfoService.find(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userInfoService.findAll());
    }

    @GetMapping("/number/point")
    public ResponseEntity<List<UserResponse>> getCurrentNumberPointByUser() {
        return ResponseEntity.ok(userInfoService.getCurrentNumberPointByUser());
    }
}
