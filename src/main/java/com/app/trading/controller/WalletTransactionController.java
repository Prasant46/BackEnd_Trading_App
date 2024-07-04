package com.app.trading.controller;

import com.app.trading.model.User;
import com.app.trading.model.Wallet;
import com.app.trading.model.WalletTransaction;
import com.app.trading.service.UserService;
import com.app.trading.service.WalletService;
import com.app.trading.service.WalletTansactionService;
import com.stripe.service.tax.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WalletTransactionController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private WalletTansactionService walletTansactionService;

    @GetMapping("/api/transactions")
    public ResponseEntity<List<WalletTransaction>> getUserWallet(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        Wallet wallet=walletService.getUserWallet(user);

        List<WalletTransaction> transactionList= walletTansactionService.getTransactionsByWallet(wallet);

        return new ResponseEntity<>(transactionList, HttpStatus.OK);

    }
}
