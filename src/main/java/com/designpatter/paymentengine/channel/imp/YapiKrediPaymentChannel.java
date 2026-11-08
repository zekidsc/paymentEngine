package com.designpatter.paymentengine.channel.imp;


import com.designpatter.paymentengine.channel.PaymentChannel;
import com.designpatter.paymentengine.config.GatewayProperties;
import com.designpatter.paymentengine.domain.PaymentRequest;
import com.designpatter.paymentengine.domain.PaymentResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public class YapiKrediPaymentChannel implements PaymentChannel {

    private final GatewayProperties.BankConfig config;

    public YapiKrediPaymentChannel(GatewayProperties.BankConfig config) {
        this.config = config;
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        String authCode = "YK_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String txId = "YK-TX-" + System.currentTimeMillis();

        return new PaymentResponse(
                true,
                txId,
                "YAPIKREDI",
                request.orderReference(),
                request.amount(),
                authCode,
                "Yapı Kredi POSnet işlemi onaylandı.",
                LocalDateTime.now()
        );
    }
}
