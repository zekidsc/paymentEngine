package com.designpatter.paymentengine;


import com.designpatter.paymentengine.domain.Currency;
import com.designpatter.paymentengine.domain.PaymentRequest;
import com.designpatter.paymentengine.domain.PaymentResponse;
import com.designpatter.paymentengine.domain.RefundRequest;
import com.designpatter.paymentengine.domain.RefundResponse;
import com.designpatter.paymentengine.service.PaymentGatewayService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentGatewayServiceTest {

    @Autowired
    private PaymentGatewayService paymentGatewayService;

    @Test
    @DisplayName("Garanti BIN'i ile ödeme yapıldığında Garanti kanalı seçilmeli ve işlem başarılı olmalıdır")
    void shouldRouteToGarantiWhenGarantiBinProvided() {
        PaymentRequest request = new PaymentRequest(
                "5549601234567890",
                "12/28",
                "321",
                new BigDecimal("1250.75"),
                Currency.TRY,
                "ORD-2026-001"
        );

        PaymentResponse response = paymentGatewayService.pay(request);

        assertNotNull(response);
        assertTrue(response.success());
        assertEquals("GARANTI", response.bankCode());
        assertEquals(new BigDecimal("1250.75"), response.amount());
        assertTrue(response.transactionId().startsWith("GR-TX-"));
    }

    @Test
    @DisplayName("Yapı Kredi BIN'i ile ödeme yapıldığında Yapı Kredi kanalı seçilmelidir")
    void shouldRouteToYapiKrediWhenYapiKrediBinProvided() {
        PaymentRequest request = new PaymentRequest(
                "4506349988776655",
                "09/27",
                "778",
                new BigDecimal("450.00"),
                Currency.TRY,
                "ORD-2026-002"
        );

        PaymentResponse response = paymentGatewayService.pay(request);

        assertNotNull(response);
        assertTrue(response.success());
        assertEquals("YAPIKREDI", response.bankCode());
        assertTrue(response.transactionId().startsWith("YK-TX-"));
    }

    @Test
    @DisplayName("Akbank iade talebinde doğru iade kanalı çalışmalıdır")
    void shouldProcessAkbankRefundSuccessfully() {
        RefundRequest request = new RefundRequest(
                "AK-TX-1700001122",
                new BigDecimal("200.00"),
                "AKBANK"
        );

        RefundResponse response = paymentGatewayService.refund(request);

        assertNotNull(response);
        assertTrue(response.success());
        assertTrue(response.refundReference().startsWith("AK-REF-"));
        assertEquals(new BigDecimal("200.00"), response.refundedAmount());
    }
}
