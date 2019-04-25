package com.yuan.server.service.mongo;

import com.yuan.server.pojo.ServerGroup;
import com.yuan.server.pojo.ServerMountDirectory;
import com.yuan.server.service.ServerMountDiretoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerMountDiretoryServiceMongoImpl implements ServerMountDiretoryService {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean add(ServerMountDirectory serverMountDirectory) {
        return mongoTemplate.insert(serverMountDirectory) != null;
    }

    @Override
    public List<?> pageList(Class<?> entityClass, int start, int size, String queryStr, Criteria condition) {
        return null;
    }

    @Override
    public List<ServerMountDirectory> all() {
        return mongoTemplate.findAll(ServerMountDirectory.class);
    }
}
