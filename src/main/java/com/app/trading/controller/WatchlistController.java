package com.app.trading.controller;

import com.app.trading.model.Coin;
import com.app.trading.model.User;
import com.app.trading.model.Watchlist;
import com.app.trading.service.CoinService;
import com.app.trading.service.UserService;
import com.app.trading.service.WatchlistService;
import com.app.trading.service.WatchlistServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<Watchlist> getUserWatchlist(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        Watchlist watchlist = watchlistService.findUserWatchlist(user.getId());

        return  ResponseEntity.ok(watchlist);

    }

    @GetMapping("/{watchlistId}")
    public ResponseEntity<Watchlist> findWatchlist(
            @PathVariable Long watchlistId) throws Exception {

        Watchlist watchlist = watchlistService.findById(watchlistId);

        return  ResponseEntity.ok(watchlist);

    }

    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchlist(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String coinId) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        Coin coin= coinService.findById(coinId);

        Coin addedCoin = watchlistService.addItemToWatchlist(coin,user);

        return  ResponseEntity.ok(addedCoin);

    }
}
