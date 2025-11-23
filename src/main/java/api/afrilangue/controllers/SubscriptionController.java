package api.afrilangue.controllers;

import api.afrilangue.dto.request.SubscriptionRequest;
import api.afrilangue.dto.request.VoucherRequest;
import api.afrilangue.dto.response.DefaultResponse;
import api.afrilangue.models.Subscription;
import api.afrilangue.serrvices.subscription.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/subscription")
public class SubscriptionController {
    private final SubscriptionService service;

    @PostMapping("/")
    public ResponseEntity<Subscription> createOne(@RequestBody SubscriptionRequest subscriptionRequest) {
        return ResponseEntity.ok(service.create(subscriptionRequest));
    }

    @GetMapping("/")
    public ResponseEntity<List<Subscription>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> findOne(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.find(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Subscription>> findSubscriptionByUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.findSubscriptionByUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse> deleteOne(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DefaultResponse> updateOne(@PathVariable("id") String id,
                                                     @RequestBody Subscription subscription) {
        return ResponseEntity.ok(service.update(id, subscription));
    }

    @PostMapping("/voucher")
    public ResponseEntity<String> checkVoucher(@RequestBody VoucherRequest voucherRequest) {
        return ResponseEntity.ok(service.checkVoucher(voucherRequest.getCode()));
    }


}
