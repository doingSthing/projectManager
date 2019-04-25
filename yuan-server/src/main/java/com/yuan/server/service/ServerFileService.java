package com.yuan.server.service;

import com.yuan.server.pojo.ServerFile;

import java.util.List;

public interface ServerFileService {

    /**
     * 如果存在便更新，否则新增
     * @param serverFile
     * @return
     */
    boolean upsert(ServerFile serverFile);

    /**
     * 获取服务器目录
     * @param serverId
     * @param path
     * @return
     */
    List<ServerFile> getSonsServerFile(String serverId, String path);

}
