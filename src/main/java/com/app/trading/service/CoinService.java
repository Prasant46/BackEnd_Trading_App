package com.app.trading.service;

import com.app.trading.model.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getCoinlist(int page) throws Exception;

    String getMarketChart(String coinId,int days) throws Exception;

    String getCoinDatails(String coinId) throws Exception;

    Coin findById(String coinId) throws Exception;

    String searchCoin(String keyword) throws Exception;

    String getTop50CoinsByMarketCapRank() throws Exception;

    String getTreadingCoins() throws Exception;
}
