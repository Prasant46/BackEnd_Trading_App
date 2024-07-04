package com.app.trading.request;

import com.app.trading.domain.OrderType;
import lombok.Data;

@Data
public class CreateOrderRequest {

    private String coinId;
    private double quantity;
    private OrderType orderType;
}
