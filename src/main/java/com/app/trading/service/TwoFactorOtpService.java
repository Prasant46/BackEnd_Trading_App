package com.app.trading.service;

import com.app.trading.model.TwoFactorOTP;
import com.app.trading.model.User;

public interface TwoFactorOtpService {

    TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt);

    TwoFactorOTP findByUser(Long userId);

    TwoFactorOTP  findById(String id);

    boolean varifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp );

    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP);

}
