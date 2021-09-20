package com.example.tacocloud.jms;

import com.example.tacocloud.orders.Order;
import org.apache.logging.log4j.message.Message;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import java.util.Objects;

public class JmsOrderReceiver implements OrderReceiver {
    private JmsTemplate jms;
    private MessageConverter converter;

    public JmsOrderReceiver(JmsTemplate jms, MessageConverter converter) {
        this.jms = jms;
        this.converter = converter;
    }

    public Order receiveOrder() throws JMSException {
        var message = jms.receive("tacocloud.order.queue");
        return (Order) converter.fromMessage(Objects.requireNonNull(message));
    }
}
