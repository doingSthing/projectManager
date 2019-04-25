package com.yuan.server.pojo;

import org.springframework.data.annotation.Id;

import java.util.List;

/**服务器*/
public class Server {

    @Id
    String _id;
    String name;
    String account;
    String password;
    String serverGroupId;
    public static final int WINDOWS = 1;
    public static final int LINUX =  2;
    int type = WINDOWS;
    /**多个ip用逗号隔开*/
    String ip;
    /**操作命令*/
    List<String> operationIds;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setServerGroupId(String serverGroupId) {
        this.serverGroupId = serverGroupId;
    }

    public String getServerGroupId() {
        return serverGroupId;
    }

    public void setOperationIds(List<String> operationIds) {
        this.operationIds = operationIds;
    }

    public List<String> getOperationIds() {
        return operationIds;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
