package com.yuan.server.service;

import com.yuan.server.pojo.Server;
import com.yuan.server.pojo.ServerDirectory;
import com.yuan.server.pojo.ServerFile;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public interface ServerService {

    /**
     * 新增服务器
     * @param server
     * @return
     */
    boolean add(Server server);

    /**
     * 新增服务器且添加至集群中
     * @param server
     * @param serverGroupId
     * @return
     */
    boolean addAndInsertToServerGroup(Server server, String serverGroupId);

    /**
     * 将某一个服务器添加至集群中
     * @param serverId
     * @param serverGroupId
     * @return
     */
    boolean insertToServerGroup(String serverId, String serverGroupId);

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
    List<Server> all(String serverGroupId);

    /**
     * 根据id获取
     * @param id
     * @return
     */
    Server getById(String id);

    /**
     * 保存，id必须存在
     * @param server
     * @return
     */
    boolean save(Server server);

    /**
     * 刷新服务器路径下的所有一级子目录(同时会刷新子文件)
     * @param serverPath
     * @return
     */
    boolean refreshSonsServerDirectory(String serverId, String serverPath);

    /**
     * 获取服务器路径下的所有一级子目录
     * @param serverPath
     * @return
     */
    List<ServerDirectory> getSonsServerDirectory(String serverId, String serverPath);


    /**
     * 获取服务器路径下所有一级子文件
     * @param serverPath
     * @return
     */
    List<ServerFile> getSonsServerFile(String serverId, String serverPath);

}
