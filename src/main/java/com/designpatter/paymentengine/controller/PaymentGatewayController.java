package com.designpatter.paymentengine.controller;


import com.designpatter.paymentengine.domain.PaymentRequest;
import com.designpatter.paymentengine.domain.PaymentResponse;
import com.designpatter.paymentengine.domain.RefundRequest;
import com.designpatter.paymentengine.domain.RefundResponse;
import com.designpatter.paymentengine.service.PaymentGatewayService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gateway")
public class PaymentGatewayController {

    private final PaymentGatewayService gatewayService;

    public PaymentGatewayController(PaymentGatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @PostMapping("/pay")
    public ResponseEntity<PaymentResponse> pay(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = gatewayService.pay(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refund")
    public ResponseEntity<RefundResponse> refund(@Valid @RequestBody RefundRequest request) {
        RefundResponse response = gatewayService.refund(request);
        return ResponseEntity.ok(response);
    }
}
