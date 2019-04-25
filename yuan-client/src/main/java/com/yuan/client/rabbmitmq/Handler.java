package com.yuan.client.rabbmitmq;

import com.yuan.client.util.SpringBootTool;
import com.yuan.constant.Command;
import com.yuan.constant.Constant;
import com.yuan.constant.Message;
import com.yuan.constant.Result;
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
    /**虚拟的命令*/
    static final int COMMAND_TYPE_VIRTUAL = 1;
    /**真实的命令*/
    static final int COMMAND_TYPE_REAL = 2;

    Logger logger = Logger.getLogger(this.getClass());

    @RabbitHandler
    @RabbitListener(queues = RabbmitMqConfig.YUAN_CLIENT_QUEUE)
    public void handler(Message message) {
        int type = message.getType();
        if(type == COMMAND_TYPE_VIRTUAL)
        {
            try {
                Command command = (Command)SpringBootTool.getApplicationContext().getBean(message.getCommand());
                logger.debug("客户端执行：" + message.getCommand());
                String[] params = message.getParams();
                command.exec(params);
            }catch (Exception e)
            {
                logger.error("该程序中没有这样的命令" + message.toString());
                logger.error(e.getMessage(), e);
            }
        }else if(type == COMMAND_TYPE_REAL)
        {

        }
    }

    /**
     * 向server中发送相应
     * @param result
     */
    public void send(Result result)
    {
        amqpTemplate.convertAndSend(Constant.YUAN_SERVER_FANOUT_EXCHANGE, "", result);
    }


}
