package com.app.trading.service;

import com.app.trading.domain.WalletTransactionType;
import com.app.trading.model.Wallet;
import com.app.trading.model.WalletTransaction;
import com.app.trading.repository.WalletRepo;
import com.app.trading.repository.WalletTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class WalletTransactionServiceImpl implements WalletTansactionService {

    @Autowired
    private WalletTransactionRepo walletTransactionRepo;


    @Autowired
    private WalletRepo walletRepo;

    @Override
    public WalletTransaction createTransaction(Wallet senderWallet,
                                    WalletTransactionType walletTransactionType,
                                    Long receiverWalletId,
                                    String purpose,
                                    Long amount) throws Exception {


        // Create transaction record
        WalletTransaction transaction = new WalletTransaction();

        transaction.setWallet(senderWallet);
        transaction.setType(walletTransactionType);
        transaction.setPurpose(purpose);
        transaction.setAmount(amount);
        transaction.setDate(LocalDate.from(LocalDateTime.now()));

        // Generate and set transferId as a String
        String transferId = UUID.randomUUID().toString();
        transaction.setTransferId(transferId);

        return walletTransactionRepo.save(transaction);    }

    @Override
    public List<WalletTransaction> getTransactionsByWallet(Wallet wallet) {
        List<WalletTransaction> walletTransactions = walletTransactionRepo.findWalletTransactionByWallet(wallet);
        return walletTransactions;
    }
}
