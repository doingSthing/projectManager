package com.yuan.server.controller;

import com.yuan.server.pojo.ServerGroup;
import com.yuan.server.service.ServerGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/servergroup")
public class ServerGroupController {

    @Autowired
    ServerGroupService serverGroupService;

    @RequestMapping("/all")
    public List<ServerGroup> all()
    {
        return serverGroupService.all();
    }

    @RequestMapping("/add")
    public boolean add(ServerGroup serverGroup)
    {
        return serverGroupService.add(serverGroup);
    }

    @RequestMapping("/getById")
    public ServerGroup getById(String id)
    {
        return serverGroupService.getById(id);
    }

}
