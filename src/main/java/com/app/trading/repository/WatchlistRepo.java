package com.app.trading.repository;

import com.app.trading.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepo extends JpaRepository<Watchlist,Long> {

    Watchlist findByUserId(Long userId);
}
