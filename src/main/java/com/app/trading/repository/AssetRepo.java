package com.app.trading.repository;

import com.app.trading.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepo extends JpaRepository<Asset, Long> {

    List<Asset> findByUserId(Long useId);

    Asset findByUserIdAndCoinId (Long userId, String coinId);

}
