package com.app.trading.service;

import com.app.trading.model.Coin;
import com.app.trading.model.User;
import com.app.trading.model.Watchlist;

public interface WatchlistService {

    Watchlist findUserWatchlist(Long userId) throws Exception;

    Watchlist createWatchlist(User user);

    Watchlist findById(Long id) throws Exception;

    Coin addItemToWatchlist(Coin coin, User user) throws Exception;


}
