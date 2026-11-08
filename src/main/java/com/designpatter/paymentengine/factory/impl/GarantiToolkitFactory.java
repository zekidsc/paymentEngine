package com.designpatter.paymentengine.factory.impl;


import com.designpatter.paymentengine.channel.PaymentChannel;
import com.designpatter.paymentengine.channel.RefundChannel;
import com.designpatter.paymentengine.channel.imp.GarantiPaymentChannel;
import com.designpatter.paymentengine.channel.imp.GarantiRefundChannel;
import com.designpatter.paymentengine.config.GatewayProperties;
import com.designpatter.paymentengine.config.GatewayProperties.BankConfig;
import com.designpatter.paymentengine.factory.BankToolkitFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(name = "fintech.gateway.providers.garanti.enabled", havingValue = "true", matchIfMissing = true)
public class GarantiToolkitFactory implements BankToolkitFactory {

    private final BankConfig config;
    private static final List<String> GARANTI_BINS = List.of("554960", "540061", "540667", "520922");

    public GarantiToolkitFactory(GatewayProperties properties) {
        this.config = properties.getProviders().get("garanti");
    }

    @Override
    public String getBankCode() {
        return "GARANTI";
    }

    @Override
    public boolean supportsBin(String bin) {
        return GARANTI_BINS.stream().anyMatch(bin::startsWith);
    }

    @Override
    public PaymentChannel createPaymentChannel() {
        return new GarantiPaymentChannel(this.config);
    }

    @Override
    public RefundChannel createRefundChannel() {
        return new GarantiRefundChannel(this.config);
    }
}
