package com.yuan.client.command;

import com.yuan.client.rabbmitmq.Handler;
import com.yuan.client.rabbmitmq.RabbmitMqConfig;
import com.yuan.constant.Command;
import com.yuan.constant.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class WindowsRootList implements Command {

    @Autowired
    Handler handler;

    @Override
    public boolean exec(Object... params) {
        Result result = new Result();
        result.setCommand("saveServerRootDirectory");
        JSONObject objJsonValue = new JSONObject();

        File[] files = File.listRoots();
        JSONArray rootDirectory = new JSONArray();
        for (File f : files)
        {
            JSONObject item = new JSONObject();
            String path = f.getPath().replaceAll("\\\\", "/");
            item.put("name", getVol(path));
            item.put("path", path);
            rootDirectory.add(item);
        }
        objJsonValue.put("rootDirectory", rootDirectory);
        result.setParams(RabbmitMqConfig.serverId , objJsonValue.toString());
        handler.send(result);
        return true;
    }

    /**
     * 获取根目录卷标
     *
     * 通过执行window cmd命令获取返回信息后截取字符串获得
     * 目前测试支持中文XP、英文2003Server(32位和64位)
     * win7还没有测过
     *
     * @param filePath
     * @return
     */
    private String getVol(String filePath)
    {
        String dir = filePath.replace("/", "");
        String cmdStr = "cmd /c vol " + dir;
        String volStr = "";
        try
        {
            String strTemp;
            InputStream in = Runtime.getRuntime().exec(cmdStr).getInputStream();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(in, "GBK"));
            while ((strTemp = bufferedReader.readLine()) != null)
            {
                volStr += strTemp;
            }
            /*
             * 中文操作系统
             */
            if (volStr.startsWith(" 驱动器") && !volStr.contains("没有标签"))
            {
                return volStr.substring(volStr.indexOf("中的卷是") + 4,
                        volStr.indexOf("卷的序列号是")).trim()
                        + "(" + dir + ")";
            }
            /*
             * 英文操作系统
             */
            else if (volStr.startsWith(" Volume in drive")
                    && !volStr.contains("has no label"))
            {
                return volStr.substring(volStr.indexOf("is") + 2,
                        volStr.indexOf("Volume Serial Number")).trim()
                        + "(" + dir + ")";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return dir;
        }
        return dir;
    }
}
