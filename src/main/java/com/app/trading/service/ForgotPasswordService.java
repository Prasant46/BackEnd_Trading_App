package com.app.trading.service;

import com.app.trading.domain.VarificationType;
import com.app.trading.model.ForgotPasswordToken;
import com.app.trading.model.User;
import org.springframework.context.annotation.Bean;

public interface ForgotPasswordService {
    ForgotPasswordToken createToken(User user,
                                    String id, String otp,
                                    VarificationType varificationType,
                                    String sendTo);
    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);
}

