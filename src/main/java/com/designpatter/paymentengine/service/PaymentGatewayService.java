package com.designpatter.paymentengine.service;


import com.designpatter.paymentengine.channel.PaymentChannel;
import com.designpatter.paymentengine.channel.RefundChannel;
import com.designpatter.paymentengine.domain.PaymentRequest;
import com.designpatter.paymentengine.domain.PaymentResponse;
import com.designpatter.paymentengine.domain.RefundRequest;
import com.designpatter.paymentengine.domain.RefundResponse;
import com.designpatter.paymentengine.factory.BankFactoryRegistry;
import com.designpatter.paymentengine.factory.BankToolkitFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayService {

    private static final Logger log = LoggerFactory.getLogger(PaymentGatewayService.class);
    private final BankFactoryRegistry factoryRegistry;

    public PaymentGatewayService(BankFactoryRegistry factoryRegistry) {
        this.factoryRegistry = factoryRegistry;
    }

    public PaymentResponse pay(PaymentRequest request) {
        String cleanCard = request.cardNumber().replaceAll("\\s+", "");
        String bin = cleanCard.substring(0, 6);

        log.info("Ödeme talebi alındı. Sipariş Ref: {}, BIN: {}", request.orderReference(), bin);

        // Factory Method ile fabrikanın çözülmesi
        BankToolkitFactory factory = factoryRegistry.resolveByBin(bin);
        log.info("Yönlendirilen Banka Fabrikası: {}", factory.getBankCode());

        // Abstract Factory üzerinden ödeme kanalının üretilmesi
        PaymentChannel channel = factory.createPaymentChannel();
        PaymentResponse response = channel.processPayment(request);

        log.info("Ödeme tamamlandı. TxID: {}, Durum: {}", response.transactionId(), response.success());
        return response;
    }

    public RefundResponse refund(RefundRequest request) {
        log.info("İade talebi alındı. Orijinal TxID: {}, Banka: {}", request.transactionId(), request.bankCode());

        BankToolkitFactory factory = factoryRegistry.resolveByBankCode(request.bankCode());
        RefundChannel channel = factory.createRefundChannel();
        RefundResponse response = channel.processRefund(request);

        log.info("İade tamamlandı. İade Ref: {}", response.refundReference());
        return response;
    }
}
