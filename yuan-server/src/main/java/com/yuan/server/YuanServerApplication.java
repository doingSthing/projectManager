package com.yuan.server;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YuanServerApplication {

    static Logger logger = Logger.getLogger(YuanServerApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(YuanServerApplication.class, args);
        }catch (Exception e)
        {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

}
