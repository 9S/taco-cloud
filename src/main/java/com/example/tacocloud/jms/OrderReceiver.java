package com.example.tacocloud.jms;

import com.example.tacocloud.orders.Order;

import javax.jms.JMSException;

public interface OrderReceiver {
    Order receiveOrder() throws JMSException;
}
