package com.designpatter.paymentengine.factory.impl;


import com.designpatter.paymentengine.channel.PaymentChannel;
import com.designpatter.paymentengine.channel.RefundChannel;
import com.designpatter.paymentengine.channel.imp.AkbankPaymentChannel;
import com.designpatter.paymentengine.channel.imp.AkbankRefundChannel;
import com.designpatter.paymentengine.config.GatewayProperties;
import com.designpatter.paymentengine.config.GatewayProperties.BankConfig;
import com.designpatter.paymentengine.factory.BankToolkitFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(name = "fintech.gateway.providers.akbank.enabled", havingValue = "true", matchIfMissing = true)
public class AkbankToolkitFactory implements BankToolkitFactory {

    private final BankConfig config;
    private static final List<String> AKBANK_BINS = List.of("435508", "557113", "454360", "516888");

    public AkbankToolkitFactory(GatewayProperties properties) {
        this.config = properties.getProviders().get("akbank");
    }

    @Override
    public String getBankCode() {
        return "AKBANK";
    }

    @Override
    public boolean supportsBin(String bin) {
        return AKBANK_BINS.stream().anyMatch(bin::startsWith);
    }

    @Override
    public PaymentChannel createPaymentChannel() {
        return new AkbankPaymentChannel(this.config);
    }

    @Override
    public RefundChannel createRefundChannel() {
        return new AkbankRefundChannel(this.config);
    }
}
