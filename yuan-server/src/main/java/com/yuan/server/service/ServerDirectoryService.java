package com.yuan.server.service;

import com.yuan.server.pojo.ServerDirectory;

import java.util.List;

public interface ServerDirectoryService {

    /**
     * 如果存在便更新，否则新增
     * @param serverDirectory
     * @return
     */
    boolean upsert(ServerDirectory serverDirectory);

    /**
     * 获取服务器目录
     * @param serverId
     * @param path
     * @return
     */
    List<ServerDirectory> getSonsServerDirectory(String serverId, String path);

    /**
     * 这里仅仅是删除server端集合中的数据，并非删除文件
     * @param serverId
     * @param path
     * @return
     */
    boolean removeServerDirectory(String serverId, String path);


}
