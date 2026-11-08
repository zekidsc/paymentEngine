package com.designpatter.paymentengine.channel.imp;

import com.designpatter.paymentengine.channel.RefundChannel;
import com.designpatter.paymentengine.config.GatewayProperties;
import com.designpatter.paymentengine.domain.RefundRequest;
import com.designpatter.paymentengine.domain.RefundResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public class GarantiRefundChannel implements RefundChannel {

    private final GatewayProperties.BankConfig config;

    public GarantiRefundChannel(GatewayProperties.BankConfig config) {
        this.config = config;
    }

    @Override
    public RefundResponse processRefund(RefundRequest request) {
        String refundRef = "GR-REF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new RefundResponse(
                true,
                refundRef,
                request.transactionId(),
                request.amount(),
                "Garanti iade talebi onaylandı.",
                LocalDateTime.now()
        );
    }
}