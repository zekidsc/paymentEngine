package com.designpatter.paymentengine.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        boolean success,
        String transactionId,
        String bankCode,
        String orderReference,
        BigDecimal amount,
        String authorizationCode,
        String message,
        LocalDateTime processedAt
) {}