package com.app.trading.repository;

import com.app.trading.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepo extends JpaRepository<Coin,String> {
}
