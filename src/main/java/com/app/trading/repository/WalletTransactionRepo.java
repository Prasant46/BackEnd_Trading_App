package com.app.trading.repository;

import com.app.trading.model.Wallet;
import com.app.trading.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletTransactionRepo extends JpaRepository<WalletTransaction,Long> {

    List<WalletTransaction> findWalletTransactionByWallet(Wallet wallet);

}
