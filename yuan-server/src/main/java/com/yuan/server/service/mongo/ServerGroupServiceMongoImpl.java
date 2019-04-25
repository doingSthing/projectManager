package com.yuan.server.service.mongo;

import com.yuan.server.pojo.ServerGroup;
import com.yuan.server.service.ServerGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerGroupServiceMongoImpl implements ServerGroupService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(ServerGroup serverGroup) {
        return mongoTemplate.insert(serverGroup) != null;
    }

    @Override
    public List<?> pageList(Class<?> entityClass, int start, int size, String queryStr, Criteria condition) {
        return null;
    }

    @Override
    public List<ServerGroup> all() {
        return mongoTemplate.findAll(ServerGroup.class);
    }

    @Override
    public ServerGroup getById(String id) {
        return mongoTemplate.findById(id, ServerGroup.class);
    }

    @Override
    public boolean save(ServerGroup serverGroup) {
        return mongoTemplate.save(serverGroup) != null;
    }
}
