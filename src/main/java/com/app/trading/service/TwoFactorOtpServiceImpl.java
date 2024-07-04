package com.app.trading.service;

import com.app.trading.model.TwoFactorOTP;
import com.app.trading.model.User;
import com.app.trading.repository.TwoFactorOtpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService{

    @Autowired
    private TwoFactorOtpRepo twoFactorOtpRepo;

    @Override
    public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) {

        UUID uuid = UUID.randomUUID();

        String id = uuid.toString();

        TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
        twoFactorOTP.setUser(user);
        twoFactorOTP.setOtp(otp);
        twoFactorOTP.setJwt(jwt);
        twoFactorOTP.setId(id);
        return twoFactorOtpRepo.save(twoFactorOTP);
    }

    @Override
    public TwoFactorOTP findByUser(Long userId) {
        return twoFactorOtpRepo.findByUserId(userId);
    }

    @Override
    public TwoFactorOTP findById(String id) {
        Optional<TwoFactorOTP> opt =twoFactorOtpRepo.findById(id);
        return opt.orElse(null);
    }

    @Override
    public boolean varifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp) {
        return twoFactorOTP.getOtp().equals(otp);
    }

    @Override
    public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP) {
        twoFactorOtpRepo.delete(twoFactorOTP);

    }
}
