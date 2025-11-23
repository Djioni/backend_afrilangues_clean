package api.afrilangue.controllers;

import api.afrilangue.dto.request.AdvertisementRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.models.Advertisement;
import api.afrilangue.serrvices.advertisement.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/advertisement")
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementService service;


    @PostMapping("/")
    public ResponseEntity<Advertisement> createOne(@RequestBody AdvertisementRequest advertisementRequest) {
        return ResponseEntity.ok(service.create(advertisementRequest));
    }

    @GetMapping("/")
    public ResponseEntity<List<Advertisement>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.find(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse> deleteOne(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DefaultResponse> updateOne(@PathVariable("id") String id,
                                                     @RequestBody Advertisement advertisement) {
        return ResponseEntity.ok(service.update(id, advertisement));
    }

}
