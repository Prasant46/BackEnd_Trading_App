package com.app.trading.service;

import com.app.trading.model.User;
import com.app.trading.model.Withdrawal;

import java.util.List;

public interface WithdrawalService {
    Withdrawal requestWithdrawal(Long amount, User user);

    Withdrawal proceedWithWithdrawal(Long withdrawalId, boolean accept) throws Exception;

    List<Withdrawal> getUsersWithdrawalHistory(User user);

    List<Withdrawal> getAllWithdrawalRequest();

}
