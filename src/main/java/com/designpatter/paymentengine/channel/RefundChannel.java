package com.designpatter.paymentengine.channel;

import com.designpatter.paymentengine.domain.RefundRequest;
import com.designpatter.paymentengine.domain.RefundResponse;

public interface RefundChannel {
    RefundResponse processRefund(RefundRequest request);
}