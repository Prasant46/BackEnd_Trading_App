package com.app.trading.controller;

import com.app.trading.domain.WalletTransactionType;
import com.app.trading.model.User;
import com.app.trading.model.Wallet;
import com.app.trading.model.WalletTransaction;
import com.app.trading.model.Withdrawal;
import com.app.trading.service.UserService;
import com.app.trading.service.WalletService;
import com.app.trading.service.WalletTansactionService;
import com.app.trading.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WithdrawalController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private UserService userService;

    @Autowired
    private WalletTansactionService walletTansactionService;

//    @Autowired
//    private WalletTransactionService walletTransactionService;

    @PostMapping("/api/withdrawal/{amount}")
    public ResponseEntity<?> withdrawalRequest(
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user= userService.findUserProfileByJwt(jwt);
        Wallet userWallet = walletService.getUserWallet(user);

        Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount,user);
        walletService.addBalanceToWallet(userWallet,-withdrawal.getAmount());

        WalletTransaction walletTransaction = walletTansactionService.createTransaction(
                userWallet,
                WalletTransactionType.WITHDRAWAL,
                null,
                "bank acount withdrawal",
                withdrawal.getAmount());

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);

    }

    @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(
            @PathVariable Long id,
            @PathVariable boolean accept,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user= userService.findUserProfileByJwt(jwt);

        Withdrawal withdrawal = withdrawalService.proceedWithWithdrawal(id,accept);

        Wallet userWallet= walletService.getUserWallet(user);

        if (!accept){
            walletService.addBalanceToWallet(userWallet,withdrawal.getAmount());
        }
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);

    }

    @GetMapping("/api/withdrawal")
    public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user= userService.findUserProfileByJwt(jwt);

        List<Withdrawal> withdrawal = withdrawalService.getUsersWithdrawalHistory(user);

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping("/api/admin/withdrawal")
    public ResponseEntity<?> getAllWithdrawalRequest(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user= userService.findUserProfileByJwt(jwt);

        List<Withdrawal> withdrawal = withdrawalService.getAllWithdrawalRequest();

        return new ResponseEntity<>(withdrawal, HttpStatus.OK);

    }

}
