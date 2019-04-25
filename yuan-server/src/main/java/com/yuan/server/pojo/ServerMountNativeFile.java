package com.yuan.server.pojo;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class ServerMountNativeFile {

    @Id
    String _id;
    String path;
    String name;
    Date createDate;
    Date updateDate;
    long size;
    String serverMountNativeFileHistoryId;
    /**云端备份路径*/
    String cloudBackupPath;

    public void setCloudBackupPath(String cloudBackupPath) {
        this.cloudBackupPath = cloudBackupPath;
    }

    public String getCloudBackupPath() {
        return cloudBackupPath;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setServerMountNativeFileHistoryId(String serverMountNativeFileHistoryId) {
        this.serverMountNativeFileHistoryId = serverMountNativeFileHistoryId;
    }

    public String getServerMountNativeFileHistoryId() {
        return serverMountNativeFileHistoryId;
    }
}
