package com.designpatter.paymentengine.domain;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RefundRequest(
        @NotBlank(message = "İşlem referansı boş olamaz")
        String transactionId,

        @NotNull(message = "İade tutarı boş olamaz")
        @DecimalMin(value = "0.01", message = "İade tutarı 0'dan büyük olmalıdır")
        BigDecimal amount,

        @NotBlank(message = "Banka kodu zorunludur")
        String bankCode
) {}