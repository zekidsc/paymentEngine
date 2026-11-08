package com.designpatter.paymentengine.factory.impl;

import com.designpatter.paymentengine.channel.PaymentChannel;
import com.designpatter.paymentengine.channel.RefundChannel;
import com.designpatter.paymentengine.channel.imp.YapiKrediPaymentChannel;
import com.designpatter.paymentengine.channel.imp.YapiKrediRefundChannel;
import com.designpatter.paymentengine.config.GatewayProperties;
import com.designpatter.paymentengine.config.GatewayProperties.BankConfig;
import com.designpatter.paymentengine.factory.BankToolkitFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(name = "fintech.gateway.providers.yapikredi.enabled", havingValue = "true", matchIfMissing = true)
public class YapiKrediToolkitFactory implements BankToolkitFactory {

    private final BankConfig config;
    private static final List<String> YAPIKREDI_BINS = List.of("450634", "450635", "540110", "402279");

    public YapiKrediToolkitFactory(GatewayProperties properties) {
        this.config = properties.getProviders().get("yapikredi");
    }

    @Override
    public String getBankCode() {
        return "YAPIKREDI";
    }

    @Override
    public boolean supportsBin(String bin) {
        return YAPIKREDI_BINS.stream().anyMatch(bin::startsWith);
    }

    @Override
    public PaymentChannel createPaymentChannel() {
        return new YapiKrediPaymentChannel(this.config);
    }

    @Override
    public RefundChannel createRefundChannel() {
        return new YapiKrediRefundChannel(this.config);
    }
}
