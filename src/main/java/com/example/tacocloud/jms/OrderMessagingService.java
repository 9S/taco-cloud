package com.example.tacocloud.jms;

import com.example.tacocloud.orders.Order;

public interface OrderMessagingService {
    void sendOrder(Order order);
}
