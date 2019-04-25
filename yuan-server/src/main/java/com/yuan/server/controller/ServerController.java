package com.yuan.server.controller;

import com.yuan.server.pojo.Server;
import com.yuan.server.pojo.ServerDirectory;
import com.yuan.server.pojo.ServerFile;
import com.yuan.server.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/server")
public class ServerController {

    @Autowired
    ServerService serverService;

    @RequestMapping("/all")
    public List<Server> all(String serverGroupId)
    {
        return serverService.all(serverGroupId);
    }

    @RequestMapping("/add")
    public boolean add(Server server, String serverGroupId)
    {
        return serverService.addAndInsertToServerGroup(server, serverGroupId);
    }

    @RequestMapping("/refreshSonsServerDirectory")
    public boolean refeshSonsServerDirectory(String serverId, String serverPath)
    {
        return serverService.refreshSonsServerDirectory(serverId, serverPath);
    }

    @RequestMapping("/getSonsServerDirectory")
    public List<ServerDirectory> getSonsServerDirectory(String serverId, String serverPath)
    {
        return serverService.getSonsServerDirectory(serverId, serverPath);
    }

    @RequestMapping("/getSonsServerFile")
    public List<ServerFile> getSonsServerFile(String serverId, String serverPath)
    {
        return serverService.getSonsServerFile(serverId, serverPath);
    }
}
