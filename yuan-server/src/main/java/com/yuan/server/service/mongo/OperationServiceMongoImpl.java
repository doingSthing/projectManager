package com.yuan.server.service.mongo;

import com.yuan.server.pojo.Operation;
import com.yuan.server.pojo.Server;
import com.yuan.server.service.OperationService;
import com.yuan.server.service.ServerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperationServiceMongoImpl implements OperationService {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ServerService serverService;

    @Override
    public boolean add(Operation operation) {
        return mongoTemplate.insert(operation) != null;
    }

    @Override
    public boolean addCommand(String operationId, String command) {
        Operation operation = getById(operationId);
        if(operation != null)
        {
           List<String> commands = operation.getCommands();
           if(commands == null)
               commands = new ArrayList<>();
            commands.add(command);
            operation.setCommands(commands);
            return save(operation);
        }
        return false;
    }

    @Override
    public boolean addCommands(String operationId, List<String> _commands) {
        Operation operation = getById(operationId);
        if(operation != null)
        {
            List<String> commands = operation.getCommands();
            if(commands == null)
                commands = new ArrayList<>();
            commands.addAll(_commands);
            operation.setCommands(commands);
            return save(operation);
        }
        return false;
    }

    @Override
    public boolean addAndInsertToServer(Operation operation, String serverId) {
        if(add(operation))
            return insertToServer(operation.get_id(), serverId);
        return false;
    }

    @Override
    public boolean insertToServer(String operationId, String serverId) {
        Server server = serverService.getById(serverId);
        List<String> operationIds = server.getOperationIds();
        if(operationIds == null)
            operationIds = new ArrayList<>();
        if(!operationIds.contains(operationId))
        {
            operationIds.add(operationId);
            server.setOperationIds(operationIds);
            return serverService.save(server);
        }
        return false;
    }

    @Override
    public List<?> pageList(Class<?> entityClass, int start, int size, String queryStr, Criteria condition) {
        return null;
    }

    @Override
    public List<Operation> all() {
        return mongoTemplate.findAll(Operation.class);
    }

    @Override
    public Operation getById(String id) {
        return mongoTemplate.findById(id, Operation.class);
    }

    @Override
    public boolean save(Operation operation) {
        return mongoTemplate.save(operation) != null;
    }
}
