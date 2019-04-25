package com.yuan.server.command;

import com.yuan.constant.Command;
import com.yuan.server.pojo.ServerDirectory;
import com.yuan.server.service.ServerDirectoryService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveServerRootDirectory implements Command {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    ServerDirectoryService serverDirectoryService;

    @Override
    public boolean exec(Object... objects) {
        if(objects == null)
        {
            logger.debug("saveServerRootDirectory=====exec======object为null");
            return false;
        }
        logger.debug("saveServerRootDirectory=====exec======object为" + JSONArray.fromObject(objects));
        if(objects.length == 2)
        {
            Object serverId = objects[0];
            if(serverId == null)
                return false;
            JSONObject result = JSONObject.fromObject(objects[1]);
            JSONArray rootDirectory = result.getJSONArray("rootDirectory");

            for(int i = 0; rootDirectory != null && i < rootDirectory.size(); i++)
            {
                JSONObject item = rootDirectory.getJSONObject(i);
                if(item==null)
                    continue;
                String name = item.getString("name");
                String path = item.getString("path");
                ServerDirectory serverDirectory = new ServerDirectory();
                serverDirectory.setName(name);
                serverDirectory.setPath(path);
                serverDirectory.setFpath("/");
                serverDirectory.setServerId(serverId.toString());
                serverDirectoryService.upsert(serverDirectory);
            }
            return true;
        }
        return false;
    }

}
