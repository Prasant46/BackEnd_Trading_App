package com.app.trading.controller;

import com.app.trading.domain.PaymentMethod;
import com.app.trading.domain.PaymentOrder;
import com.app.trading.domain.PaymentOrderStatus;
import com.app.trading.model.User;
import com.app.trading.response.PaymentResponse;
import com.app.trading.service.PaymentService;
import com.app.trading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    PaymentService paymentService;

    @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        PaymentResponse paymentResponse;

        PaymentOrder order= paymentService.createOrder(user,amount,paymentMethod);

        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            paymentResponse=paymentService.createRazorpayPaymentLink(user,amount,order.getId());
        }
        else {
            throw new Exception("Error in payment method");
        }

        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }





}
