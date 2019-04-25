package com.yuan.client.command;

import com.yuan.client.rabbmitmq.Handler;
import com.yuan.client.rabbmitmq.RabbmitMqConfig;
import com.yuan.constant.Command;
import com.yuan.constant.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DirectoryList implements Command {

    @Autowired
    Handler handler;

    Logger logger = Logger.getLogger(this.getClass());

    @Override
    public boolean exec(Object... objects) {
        if(objects == null || objects.length != 1)
            return false;
        Result result = new Result();
        try {
            result.setCommand("saveServerDirectoryAndFile");
            JSONObject objJsonValue = new JSONObject();

            JSONArray directoryList = new JSONArray();

            String serverPath = objects[0].toString();
            File directory = new File(serverPath);
            if(directory.isDirectory())
            {
                File[] files = directory.listFiles();
                for(int i = 0; files!=null && i < files.length; i++)
                {
                    File file = files[i];

                    JSONObject item = new JSONObject();
                    item.put("name", file.getName());
                    String path = file.getPath().replaceAll("\\\\", "/");
                    item.put("path", path);
                    item.put("fpath", serverPath);
                    item.put("isDirectory", file.isDirectory());
                    directoryList.add(item);
                }
                objJsonValue.put("directoryList", directoryList);
                result.setParams(RabbmitMqConfig.serverId , objJsonValue.toString());
                handler.send(result);
            }
        }catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }

        return true;
    }

}
