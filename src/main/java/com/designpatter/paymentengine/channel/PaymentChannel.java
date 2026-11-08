package com.designpatter.paymentengine.channel;

import com.designpatter.paymentengine.domain.PaymentRequest;
import com.designpatter.paymentengine.domain.PaymentResponse;

public interface PaymentChannel {
    PaymentResponse processPayment(PaymentRequest request);
}