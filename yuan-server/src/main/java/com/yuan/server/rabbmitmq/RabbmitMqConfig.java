package com.yuan.server.rabbmitmq;

import com.yuan.constant.Constant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbmitMqConfig {

    static final String SERVER_QUEUE = "yuanServerQueue";

    @Bean
    public Queue yuanServerQueue(){
        return new Queue(SERVER_QUEUE);
    }

    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange(Constant.YUAN_SERVER_FANOUT_EXCHANGE);
    }

    @Bean
    Binding bindingServerExchange(Queue yuanServerQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(yuanServerQueue).to(fanoutExchange);
    }

}
