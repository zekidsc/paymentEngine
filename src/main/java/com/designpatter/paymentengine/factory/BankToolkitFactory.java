package com.designpatter.paymentengine.factory;


import com.designpatter.paymentengine.channel.PaymentChannel;
import com.designpatter.paymentengine.channel.RefundChannel;

public interface BankToolkitFactory {
    String getBankCode();
    boolean supportsBin(String bin);
    PaymentChannel createPaymentChannel();
    RefundChannel createRefundChannel();
}
