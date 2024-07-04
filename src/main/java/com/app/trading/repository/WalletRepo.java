package com.app.trading.repository;

import com.app.trading.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepo extends JpaRepository<Wallet,Long> {

    Wallet findByUserId(Long userId);
}
