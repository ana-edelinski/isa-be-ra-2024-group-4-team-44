package com.example.advertisingreceiver.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class RabbitConfig {
    public static final String EXCHANGE_NAME = "advertisingExchange";
    public static final String QUEUE_NAME = "advertisingQueue";

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue queue() {
        return new AnonymousQueue();
    }

    @Bean
    Binding binding(FanoutExchange fanoutExchange, Queue queue) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }
}
