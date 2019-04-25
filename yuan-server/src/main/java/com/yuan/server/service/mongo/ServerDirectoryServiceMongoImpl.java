package com.yuan.server.service.mongo;

import com.yuan.server.pojo.ServerDirectory;
import com.yuan.server.service.ServerDirectoryService;
import com.yuan.server.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerDirectoryServiceMongoImpl implements ServerDirectoryService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean upsert(ServerDirectory serverDirectory) {
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("serverId").is(serverDirectory.getServerId()),
                        Criteria.where("path").is(serverDirectory.getPath())
                )
        );
        Update update = EntityUtil.convertMongoUpdate(serverDirectory);
        mongoTemplate.upsert(query, update, ServerDirectory.class);
        return false;
    }

    @Override
    public List<ServerDirectory> getSonsServerDirectory(String serverId, String path) {
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("serverId").is(serverId),
                        Criteria.where("fpath").is(path)
                )
        );
        return mongoTemplate.find(query, ServerDirectory.class);
    }

    @Override
    public boolean removeServerDirectory(String serverId, String path) {

        return false;
    }
}
