package com.example.tacocloud.jms;

import com.example.tacocloud.orders.Order;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {
    private final JmsTemplate jms;
    private Destination orderQueue;

    private Message addOrderSource(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        return message;
    }

    @Autowired
    public void setOrderQueue(Destination orderQueue) {
        this.orderQueue = orderQueue;
    }

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jms) {
        this.jms = jms;
    }

    @Override
    public void sendOrder(Order order) {
        jms.convertAndSend(orderQueue, order, this::addOrderSource);
    }

    @Bean
    public Destination orderQueue() {
        return new ActiveMQQueue("tacocloud.order.queue");
    }

    /*@GetMapping("/convertAndSend/order")
    public String convertAndSendOrder() {
        Order order = buildOrder();
        jms.convertAndSend("tacocloud.order.queue", order,
                this::addOrderSource);
        return "Convert and sent order";
    }*/
}
