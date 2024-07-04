package com.app.trading.service;

import com.app.trading.model.Order;
import com.app.trading.model.User;
import com.app.trading.model.Wallet;

public interface WalletService {

    Wallet getUserWallet (User user);

    Wallet addBalanceToWallet(Wallet wallet,Long money);

    Wallet findWalletById(Long id) throws Exception;

    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception;

    Wallet payOrderPayment(Order order, User user) throws Exception;


}
