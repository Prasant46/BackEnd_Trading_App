package com.app.trading.repository;

import com.app.trading.model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawalRepo extends JpaRepository<Withdrawal,Long> {
    List<Withdrawal> findByUserId(long userId);
}
