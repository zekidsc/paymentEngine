package com.designpatter.paymentengine.domain;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record PaymentRequest(
        @NotBlank(message = "Kart numarası boş olamaz")
        @Pattern(regexp = "^[0-9]{16}$", message = "Kart numarası 16 haneli rakam olmalıdır")
        String cardNumber,

        @NotBlank(message = "Son kullanma tarihi boş olamaz (AA/YY)")
        String expireDate,

        @NotBlank(message = "CVV boş olamaz")
        @Pattern(regexp = "^[0-9]{3}$", message = "CVV 3 haneli rakam olmalıdır")
        String cvv,

        @NotNull(message = "Tutar boş olamaz")
        @DecimalMin(value = "0.01", message = "Tutar 0'dan büyük olmalıdır")
        BigDecimal amount,

        @NotNull(message = "Para birimi zorunludur")
        Currency currency,

        @NotBlank(message = "Sipariş referans no boş olamaz")
        String orderReference
) {}