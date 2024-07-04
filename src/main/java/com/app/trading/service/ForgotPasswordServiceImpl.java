package com.app.trading.service;

import com.app.trading.domain.VarificationType;
import com.app.trading.model.ForgotPasswordToken;
import com.app.trading.model.User;
import com.app.trading.repository.ForgotPasswordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService{

    @Autowired
    private ForgotPasswordRepo forgotPasswordRepo;

    @Override
    public ForgotPasswordToken createToken(User user,
                                           String id, String otp,
                                           VarificationType varificationType,
                                           String sendTo) {

        ForgotPasswordToken token = new ForgotPasswordToken();
        token.setUser(user);
        token.setOtp(otp);
        token.setVarificationType(varificationType);
        token.setSendTo(sendTo);
        token.setId(id);

        return forgotPasswordRepo.save(token);
    }

    @Override
    public ForgotPasswordToken findById(String id) {

        Optional<ForgotPasswordToken> token = forgotPasswordRepo.findById(id);
        return token.orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUser(Long userId) {
        return forgotPasswordRepo.findByUserId(userId);
    }

    @Override
    public void deleteToken(ForgotPasswordToken token) {

        forgotPasswordRepo.delete(token);

    }
}
