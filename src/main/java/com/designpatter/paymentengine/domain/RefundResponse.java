package com.designpatter.paymentengine.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RefundResponse(
        boolean success,
        String refundReference,
        String originalTransactionId,
        BigDecimal refundedAmount,
        String message,
        LocalDateTime processedAt
) {}