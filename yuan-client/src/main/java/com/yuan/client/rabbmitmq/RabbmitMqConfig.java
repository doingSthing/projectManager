package com.yuan.client.rabbmitmq;

import com.yuan.constant.Constant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbmitMqConfig {

    public static final String serverId = "5cc05bbb14d74b3c88acabae";//需要校验合法性
//    public static final String serverId = "5cc05bb714d74b3c88acabad";//需要校验合法性
    private static final String QUEUE_PREFIX = "yuanClientQueue";
    static final String YUAN_CLIENT_QUEUE = QUEUE_PREFIX + serverId;
    static final String YUAN_CLIENT_FANOUT_EXCHANGE = Constant.CLIENT_FANOUT_EXCHANGE_PREFIX + serverId;

    @Bean
    public Queue yuanClientQueue() throws Exception{
        if(serverId.isEmpty())
            throw new Exception("yuan.serverId未配置，请检查配置文件");
        return new Queue(YUAN_CLIENT_QUEUE);
    }

    @Bean
    FanoutExchange fanoutExchange() throws Exception{
        if(serverId.isEmpty())
            throw new Exception("yuan.serverId未配置，请检查配置文件");
        return new FanoutExchange(YUAN_CLIENT_FANOUT_EXCHANGE);
    }

    @Bean
    Binding bindingServerExchange(Queue yuanClientQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(yuanClientQueue).to(fanoutExchange);
    }

}
