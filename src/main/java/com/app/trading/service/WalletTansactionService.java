package com.app.trading.service;

import com.app.trading.domain.WalletTransactionType;
import com.app.trading.model.Wallet;
import com.app.trading.model.WalletTransaction;

import java.util.List;

public interface WalletTansactionService {
    WalletTransaction createTransaction(Wallet senderWallet,
                                        WalletTransactionType walletTransactionType,
                                        Long receiverWalletId,
                                        String purpose,
                                        Long amount) throws Exception;

    List<WalletTransaction> getTransactionsByWallet(Wallet wallet);
}
