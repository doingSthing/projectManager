package com.yuan.server.pojo;

import org.springframework.data.annotation.Id;

public class Member {

    @Id
    String _id;
    /**会员类型*/
    int type;



}
