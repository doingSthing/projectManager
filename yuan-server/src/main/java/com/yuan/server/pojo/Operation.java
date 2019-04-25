package com.yuan.server.pojo;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Operation {

    @Id
    String _id;
    String name;
    List<String> commands;

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

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
