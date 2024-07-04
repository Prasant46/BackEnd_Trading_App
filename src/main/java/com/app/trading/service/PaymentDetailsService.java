package com.app.trading.service;

import com.app.trading.model.User;
import com.app.trading.model.PaymentDetails;

public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(String accountNumber,
                                                                  String accountHolderName,
                                                                  String ifsc,
                                                                  String bankName,
                                                                  User user);

    public PaymentDetails getUserPaymentDetails(User user);
}
