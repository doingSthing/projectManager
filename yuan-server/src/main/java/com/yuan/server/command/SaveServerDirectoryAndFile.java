package com.yuan.server.command;

import com.yuan.constant.Command;
import com.yuan.server.pojo.ServerDirectory;
import com.yuan.server.pojo.ServerFile;
import com.yuan.server.service.ServerDirectoryService;
import com.yuan.server.service.ServerFileService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveServerDirectoryAndFile implements Command {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    ServerDirectoryService serverDirectoryService;

    @Autowired
    ServerFileService serverFileService;

    @Override
    public boolean exec(Object... objects) {

        if(objects == null)
        {
            logger.debug("SaveServerDirectoryAndFile=====exec======object为null");
            return false;
        }
        logger.debug("SaveServerDirectoryAndFile=====exec======object为" + JSONArray.fromObject(objects));
        if(objects.length == 2)
        {
            Object serverId = objects[0];
            if(serverId == null)
                return false;
            JSONObject result = JSONObject.fromObject(objects[1]);
            JSONArray directoryList = result.getJSONArray("directoryList");

            for(int i = 0; directoryList != null && i < directoryList.size(); i++)
            {
                JSONObject item = directoryList.getJSONObject(i);
                if(item==null)
                    continue;
                String name = item.getString("name");
                String path = item.getString("path");
                String fpath = item.getString("fpath");
                boolean isDirectory = item.getBoolean("isDirectory");
                if(isDirectory)
                {
                    ServerDirectory serverDirectory = new ServerDirectory();
                    serverDirectory.setName(name);
                    serverDirectory.setPath(path);
                    serverDirectory.setFpath(fpath);
                    serverDirectory.setServerId(serverId.toString());
                    serverDirectoryService.upsert(serverDirectory);
                }else
                {
                    ServerFile serverFile = new ServerFile();
                    serverFile.setName(name);
                    serverFile.setPath(path);
                    serverFile.setFpath(fpath);
                    serverFile.setServerId(serverId.toString());
                    serverFileService.upsert(serverFile);
                }
            }
            return true;
        }
        return false;
    }
}
