package com.yuan.server.service.mongo;

import com.yuan.server.pojo.ServerFile;
import com.yuan.server.service.ServerFileService;
import com.yuan.server.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerFileServiceMongoImpl implements ServerFileService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean upsert(ServerFile serverFile) {
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("serverId").is(serverFile.getServerId()),
                        Criteria.where("path").is(serverFile.getPath())
                )
        );
        Update update = EntityUtil.convertMongoUpdate(serverFile);
        mongoTemplate.upsert(query, update, ServerFile.class);
        return false;
    }

    @Override
    public List<ServerFile> getSonsServerFile(String serverId, String path) {
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("serverId").is(serverId),
                        Criteria.where("fpath").is(path)
                )
        );
        return mongoTemplate.find(query, ServerFile.class);
    }
}
