package com.yuan.server.service;

import com.yuan.server.pojo.ServerMountDirectory;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public interface ServerMountDiretoryService {

    /**
     * 新增挂载目录
     * @param serverMountDirectory
     * @return
     */
    boolean add(ServerMountDirectory serverMountDirectory);

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
    List<ServerMountDirectory> all();
}
