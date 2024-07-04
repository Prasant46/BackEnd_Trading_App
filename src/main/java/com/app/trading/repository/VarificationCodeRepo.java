package com.app.trading.repository;

import com.app.trading.model.VarificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VarificationCodeRepo extends JpaRepository<VarificationCode,Long> {

    public VarificationCode findByUserId(Long userId);

}
