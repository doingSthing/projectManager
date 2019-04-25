package com.yuan.server.service;

import com.yuan.server.pojo.Operation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public interface OperationService {

    /**
     * 增加操作命令
     * @param operation
     * @return
     */
    boolean add(Operation operation);

    /**
     * 给操作添加命令
     * @param operationId
     * @param command
     * @return
     */
    boolean addCommand(String operationId, String command);

    /**
     * 给操作批量添加命令
     * @param operationId
     * @param commands
     * @return
     */
    boolean addCommands(String operationId, List<String> commands);

    /**
     * 新增且将操作命令添加至服务器中
     * @param operation
     * @param serverId
     * @return
     */
    boolean addAndInsertToServer(Operation operation, String serverId);

    /**
     * 将操作命令添加至服务器中
     * @param operationId
     * @param serverId
     * @return
     */
    boolean insertToServer(String operationId, String serverId);

    /**
     * 翻页列表
     * @param entityClass
     * @param start
     * @param size
     * @param queryStr
     * @param condition
     * @return
     */
    List<?> pageList(Class<?> entityClass, int start, int size, String queryStr, Criteria condition);

    /**
     * 查询所有
     * @return
     */
    List<Operation> all();

    /**
     * 根据id获取
     * @param id
     * @return
     */
    Operation getById(String id);

    /**
     * 保存，id必须存在
     * @param operation
     * @return
     */
    boolean save(Operation operation);
}
