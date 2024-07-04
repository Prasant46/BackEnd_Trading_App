package com.app.trading.response;

import com.app.trading.domain.WithdrawalStatus;
import com.app.trading.model.User;
import com.app.trading.model.Withdrawal;
import com.app.trading.repository.WithdrawalRepo;
import com.app.trading.service.WalletService;
import com.app.trading.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    @Autowired
    private WithdrawalRepo withdrawalRepo;

    @Override
    public Withdrawal requestWithdrawal(Long amount, User user) {
        Withdrawal withdrawal = new Withdrawal();

        withdrawal.setAmount(amount);
        withdrawal.setUser(user);
        withdrawal.setStatus(WithdrawalStatus.PENDING);
        return withdrawalRepo.save(withdrawal);
    }

    @Override
    public Withdrawal proceedWithWithdrawal(Long withdrawalId,
                                            boolean accept) throws Exception {

        Optional<Withdrawal> withdrawal = withdrawalRepo.findById(withdrawalId);

        if (withdrawal.isEmpty()){
            throw new Exception("withdrawal not found");
        }

        Withdrawal withdrawal1 = withdrawal.get();

        withdrawal1.setDate(LocalDateTime.now());

        if (accept== true){
            withdrawal1.setStatus(WithdrawalStatus.SUCCESS);
        }else {
            withdrawal1.setStatus(WithdrawalStatus.PENDING);
        }
        return withdrawalRepo.save(withdrawal1);
    }

    @Override
    public List<Withdrawal> getUsersWithdrawalHistory(User user) {
        return withdrawalRepo.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getAllWithdrawalRequest() {
        return withdrawalRepo.findAll();
    }
}
