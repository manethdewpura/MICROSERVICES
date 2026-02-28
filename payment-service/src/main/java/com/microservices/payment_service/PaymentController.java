package com.microservices.payment_service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final List<Map<String, Object>> payments = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    @GetMapping
    public List<Map<String, Object>> getPayments() {
        return payments;
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody Map<String, Object> payment) {
        Map<String, Object> paymentWithId = new HashMap<>(payment);
        paymentWithId.put("id", idCounter.getAndIncrement());
        paymentWithId.put("status", "SUCCESS");
        payments.add(paymentWithId);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentWithId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPayment(@PathVariable int id) {
        return payments.stream()
                .filter(p -> p.get("id").equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
