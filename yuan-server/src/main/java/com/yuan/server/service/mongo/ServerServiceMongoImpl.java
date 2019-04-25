package com.yuan.server.service.mongo;

import com.yuan.constant.Message;
import com.yuan.server.pojo.Server;
import com.yuan.server.pojo.ServerDirectory;
import com.yuan.server.pojo.ServerFile;
import com.yuan.server.pojo.ServerGroup;
import com.yuan.server.rabbmitmq.Handler;
import com.yuan.server.service.ServerDirectoryService;
import com.yuan.server.service.ServerFileService;
import com.yuan.server.service.ServerGroupService;
import com.yuan.server.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServerServiceMongoImpl implements ServerService {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    Handler handler;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ServerGroupService serverGroupService;

    @Autowired
    ServerDirectoryService serverDirectoryService;

    @Autowired
    ServerFileService serverFileService;

    @Override
    public boolean add(Server server) {
        return mongoTemplate.insert(server) != null;
    }

    @Override
    public boolean addAndInsertToServerGroup(Server server, String serverGroupId) {
        if(add(server))
            return insertToServerGroup(server.get_id(), serverGroupId);
        return false;
    }

    @Override
    public boolean insertToServerGroup(String serverId, String serverGroupId) {
        ServerGroup serverGroup = serverGroupService.getById(serverGroupId);
        if(serverGroup == null)
            return false;

        List<String> serverIds = serverGroup.getServerIds();
        if(serverIds == null)
            serverIds = new ArrayList<>();
        if(!serverIds.contains(serverId))
        {
            logger.debug(
                    new StringBuffer().append("serverGroup:")
                            .append(JSONObject.fromObject(serverGroup))
                            .append("中插入serverId:")
                            .append(serverId)
            );
            serverIds.add(serverId);
            serverGroup.setServerIds(serverIds);
            return serverGroupService.save(serverGroup);
        }
        logger.debug(
                new StringBuffer().append("serverGroup:")
                        .append(JSONObject.fromObject(serverGroup))
                        .append("中已经包含serverId:")
                        .append(serverId)
        );
        return false;
    }


    @Override
    public List<?> pageList(Class<?> entityClass, int start, int size, String queryStr, Criteria condition) {
        return null;
    }

    @Override
    public List<Server> all(String serverGroupId) {
        Query query = new Query();
        query.addCriteria(
                Criteria.where("serverGroupId").is(serverGroupId)
        );
        return mongoTemplate.find(query, Server.class);
    }

    @Override
    public Server getById(String id) {
        return mongoTemplate.findById(id, Server.class);
    }

    @Override
    public boolean save(Server server) {
        return mongoTemplate.save(server) != null;
    }

    @Override
    public boolean refreshSonsServerDirectory(String serverId, String serverPath) {
        Server server = getById(serverId);
        if(server == null)
            return false;
        Message message = new Message();
        if("/".equals(serverPath))
        {
            if(server.getType() != Server.LINUX)
                message.setCommand("windowsRootList");
            else
            {
                message.setCommand("directoryList");
                message.setParams(serverPath);
            }
            handler.send(message, serverId);
        }else
        {
            message.setCommand("directoryList");
            message.setParams(serverPath);
            handler.send(message, serverId);
        }
        return true;
    }

    @Override
    public List<ServerDirectory> getSonsServerDirectory(String serverId, String serverPath) {
        /**这里先弄一个假的*/
        return serverDirectoryService.getSonsServerDirectory(serverId, serverPath);
    }

    @Override
    public List<ServerFile> getSonsServerFile(String serverId, String serverPath) {
        return serverFileService.getSonsServerFile(serverId, serverPath);
    }
}
