package com.yuan.constant;

import java.io.Serializable;

/**server端发送的消息，client端接受到的消息*/
public class Message implements Serializable {

    /**虚拟的命令*/
    public static final int COMMAND_TYPE_VIRTUAL = 1;
    /**真实的命令*/
    public static final int COMMAND_TYPE_REAL = 2;

    int type = COMMAND_TYPE_VIRTUAL;
    /**这里的command是指执行客户端命令的bean*/
    String command;

    String[] params;/**参数列表*/

    public void setParams(String... params) {
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
