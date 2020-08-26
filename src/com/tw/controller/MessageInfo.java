package com.tw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("MessageInfo")
public class MessageInfo {

    /**
     * 主页查看新消息，跳转到新界面
     * @return
     */
    @RequestMapping("ToFindNewMessage")
    public String toFindNewMessage(){
        return "admin/NewMessage";
    }
}
