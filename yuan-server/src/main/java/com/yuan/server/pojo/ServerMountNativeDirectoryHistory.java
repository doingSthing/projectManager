package com.yuan.server.pojo;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

/**服务器本地目录历史*/
public class ServerMountNativeDirectoryHistory {

    @Id
    String _id;
    String name;
    String path;
    Date createDate;/**这里的时间以数据库的为准，不以本地目录的真实时间为准，因为存在还原的情况*/
    Date updateDate;/**这里的时间以数据库的为准，不以本地目录的真实时间为准，因为存在还原的情况*/
    List<String> serverMountNativeFileHistoryIds;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public void setServerMountNativeFileHistoryIds(List<String> serverMountNativeFileHistoryIds) {
        this.serverMountNativeFileHistoryIds = serverMountNativeFileHistoryIds;
    }

    public List<String> getServerMountNativeFileHistoryIds() {
        return serverMountNativeFileHistoryIds;
    }
}
