package com.app.trading.service;

import com.app.trading.domain.OrderType;
import com.app.trading.model.Order;
import com.app.trading.model.User;
import com.app.trading.model.Wallet;
import com.app.trading.repository.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    private WalletRepo walletRepo;

    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet =walletRepo.findByUserId(user.getId());

        if(wallet==null){
            wallet = new Wallet();
            wallet.setUser(user);

            wallet.setBalance(BigDecimal.ZERO);

            walletRepo.save(wallet);
        }
        return wallet;
    }

    @Override
    public Wallet addBalanceToWallet(Wallet wallet, Long money) {

        BigDecimal balance = wallet.getBalance();
        BigDecimal newBalance = balance.add(BigDecimal.valueOf(money));

        wallet.setBalance(newBalance);

        return walletRepo.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long id) throws Exception {

        Optional<Wallet> wallet = walletRepo.findById(id);

        if (wallet.isPresent()){
            return wallet.get();
        }
        throw new Exception("wallet not found");
    }

    @Override
    public Wallet walletToWalletTransfer(User sender,
                                         Wallet receiverWallet,
                                         Long amount) throws Exception {

        Wallet senderWallet = getUserWallet(sender);

        if (receiverWallet.getBalance() == null) {
            receiverWallet.setBalance(BigDecimal.ZERO);
            walletRepo.save(receiverWallet);
        }

        if (senderWallet.getBalance() == null) {
            senderWallet.setBalance(BigDecimal.ZERO);
            walletRepo.save(receiverWallet);

        }

        try{

            if (senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount))<0){
                throw new Exception("Insufficient balance...");
            }

            BigDecimal senderBalance = senderWallet.getBalance().subtract(BigDecimal.valueOf(amount));
            senderWallet.setBalance(senderBalance);
            walletRepo.save(senderWallet);

            BigDecimal receiverBalance = receiverWallet.getBalance().add(BigDecimal.valueOf(amount));

            receiverWallet.setBalance(receiverBalance);
            walletRepo.save(receiverWallet);
            return senderWallet;

        }
        catch (Exception e){

            throw new Exception("Transfer failed... Something wrong in server");
        }

    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {

        Wallet wallet = getUserWallet(user);

        if (order.getOrderType().equals(OrderType.BUY)){

            BigDecimal newbalance = wallet.getBalance().subtract(order.getPrice());

            if(newbalance.compareTo(order.getPrice())<0){
                throw new Exception("Insufficient funds for this transaction");
            }
            wallet.setBalance(newbalance);
        }
        else {
            BigDecimal newbalance = wallet.getBalance().add(order.getPrice());

            wallet.setBalance(newbalance);
        }

        walletRepo.save(wallet);
        return wallet;
    }
}
