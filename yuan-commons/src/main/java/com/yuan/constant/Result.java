package com.yuan.constant;

import java.io.Serializable;

/**client端相应的结果，server端接收到的结果*/
public class Result implements Serializable {

    /**跟Message中的一样，针对服务端的bean*/
    String command;

    String[] params;/**参数列表*/

    public void setParams(String... params) {
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

}
