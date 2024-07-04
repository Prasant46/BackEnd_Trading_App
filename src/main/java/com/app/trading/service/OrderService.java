package com.app.trading.service;

import com.app.trading.domain.OrderType;
import com.app.trading.model.Coin;
import com.app.trading.model.Order;
import com.app.trading.model.OrderItem;
import com.app.trading.model.User;
import jakarta.transaction.Transactional;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol);

    OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice);



    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;
}
