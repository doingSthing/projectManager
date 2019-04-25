package com.yuan.server.service;

import com.yuan.server.pojo.ServerGroup;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public interface ServerGroupService {

    /**
     * 新增服务器集群
     * @param serverGroup
     * @return
     */
    boolean add(ServerGroup serverGroup);

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
    List<ServerGroup> all();

    /**
     * 根据id获取
     * @param id
     * @return
     */
    ServerGroup getById(String id);

    /**
     * 保存，id必须存在
     * @param serverGroup
     * @return
     */
    boolean save(ServerGroup serverGroup);

}
