package com.app.trading.controller;

import com.app.trading.domain.PaymentOrder;
import com.app.trading.domain.WalletTransactionType;
import com.app.trading.model.Order;
import com.app.trading.model.User;
import com.app.trading.model.Wallet;
import com.app.trading.model.WalletTransaction;
import com.app.trading.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class WalletController {


    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private WalletTansactionService transactionService;

    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        return  new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);

    }

    @PutMapping("api/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long walletId,
            @RequestBody WalletTransaction req
            ) throws Exception {

        User senderUser= userService.findUserProfileByJwt(jwt);
        Wallet receiverWallet = walletService.findWalletById(walletId);
        Wallet wallet= walletService.walletToWalletTransfer(
                senderUser,receiverWallet,req.getAmount());

        transactionService.createTransaction(wallet,
                WalletTransactionType.WALLET_TRANSFER,
                receiverWallet.getId(),
                req.getPurpose(),
                req.getAmount());


        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);

    }

    @PutMapping("api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId

    ) throws Exception {

        User user= userService.findUserProfileByJwt(jwt);

        Order order = orderService.getOrderById(orderId);

        Wallet wallet = walletService.payOrderPayment(order,user);

        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);

    }

    @PutMapping("api/wallet/deposit")
    public ResponseEntity<Wallet> addBalanceToWallet(
            @RequestHeader("Authorization") String jwt,
            @RequestParam (name = "order_id") Long orderId,
            @RequestParam(name = "payment_id") String paymentId
    ) throws Exception {

        User user= userService.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        PaymentOrder order =paymentService.getPaymentOrderById(orderId);

        Boolean status=paymentService.proccedPaymentOrder(order,paymentId);

        if (wallet.getBalance()==null){
            wallet.setBalance(BigDecimal.valueOf(0));
        }

       if (status){
           wallet=walletService.addBalanceToWallet(wallet,order.getAmount());
       }
        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);

    }

}
