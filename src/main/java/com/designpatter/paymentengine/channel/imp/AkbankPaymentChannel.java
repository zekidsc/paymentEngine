package com.designpatter.paymentengine.channel.imp;

import com.designpatter.paymentengine.channel.PaymentChannel;
import com.designpatter.paymentengine.config.GatewayProperties;
import com.designpatter.paymentengine.domain.PaymentRequest;
import com.designpatter.paymentengine.domain.PaymentResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public class AkbankPaymentChannel implements PaymentChannel {

    private final GatewayProperties.BankConfig config;

    public AkbankPaymentChannel(GatewayProperties.BankConfig config) {
        this.config = config;
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        String authCode = "AK_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String txId = "AK-TX-" + System.currentTimeMillis();

        return new PaymentResponse(
                true,
                txId,
                "AKBANK",
                request.orderReference(),
                request.amount(),
                authCode,
                "Akbank Sanal POS tahsilatı tamamlandı.",
                LocalDateTime.now()
        );
    }
}