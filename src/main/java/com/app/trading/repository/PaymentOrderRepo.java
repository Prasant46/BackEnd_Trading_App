package com.app.trading.repository;

import com.app.trading.domain.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepo extends JpaRepository<PaymentOrder,Long> {
}
