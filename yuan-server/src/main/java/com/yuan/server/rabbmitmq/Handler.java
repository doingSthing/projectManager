package com.yuan.server.rabbmitmq;

import com.yuan.constant.Command;
import com.yuan.constant.Constant;
import com.yuan.constant.Message;
import com.yuan.constant.Result;
import com.yuan.server.util.SpringBootTool;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Handler {

    @Autowired
    private AmqpTemplate amqpTemplate;

    Logger logger = Logger.getLogger(this.getClass());

    @RabbitHandler
    @RabbitListener(queues = RabbmitMqConfig.SERVER_QUEUE)
    public void handler(Result result) {
        try {
            Command command = (Command) SpringBootTool.getApplicationContext().getBean(result.getCommand());
            logger.debug("服务端执行：" + result.getCommand());
            String[] params = result.getParams();
            command.exec(params);
        }catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 向server中发送相应
     */
    public void send(Message message, String serverId)
    {
        amqpTemplate.convertAndSend(Constant.CLIENT_FANOUT_EXCHANGE_PREFIX + serverId, "", message);
    }


}
