package com.yuan.server.pojo;

import org.springframework.data.annotation.Id;

import java.util.List;

/**服务器集群*/
public class ServerGroup {

    @Id
    String _id;
    String name;
    List<String> serverIds;

    public void setServerIds(List<String> serverIds) {
        this.serverIds = serverIds;
    }

    public List<String> getServerIds() {
        return serverIds;
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
}
