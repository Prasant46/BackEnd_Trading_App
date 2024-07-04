package com.app.trading.repository;

import com.app.trading.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails,Long> {

    PaymentDetails findByUserId(Long userId);
}
