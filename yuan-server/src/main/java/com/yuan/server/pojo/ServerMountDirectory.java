package com.yuan.server.pojo;

import org.springframework.data.annotation.Id;

/**服务器挂载目录*/
public class ServerMountDirectory {

    @Id
    String _id;
    /**服务器id*/
    String serverId;
    /**挂载路径*/
    String path;
    /**本地备份路径*/
    String nativeBackupPath;
    /**云端备份路径*/
    String cloudBackupPath;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNativeBackupPath() {
        return nativeBackupPath;
    }

    public void setNativeBackupPath(String nativeBackupPath) {
        this.nativeBackupPath = nativeBackupPath;
    }

    public String getCloudBackupPath() {
        return cloudBackupPath;
    }

    public void setCloudBackupPath(String cloudBackupPath) {
        this.cloudBackupPath = cloudBackupPath;
    }
}
