package com.app.trading.service;

import com.app.trading.domain.PaymentMethod;
import com.app.trading.domain.PaymentOrder;
import com.app.trading.domain.PaymentOrderStatus;
import com.app.trading.model.User;
import com.app.trading.response.PaymentResponse;
import com.razorpay.RazorpayException;

public interface PaymentService {

    PaymentOrder createOrder(User user, Long amount,
                             PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    Boolean proccedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;

    PaymentResponse createRazorpayPaymentLink(User user, Long amount, Long orderId) throws RazorpayException;











}
