package com.app.trading.repository;

import com.app.trading.model.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOtpRepo extends JpaRepository<TwoFactorOTP,String> {
    TwoFactorOTP findByUserId(Long userId);
}
