package com.tw.controller;

import com.tw.shiro.realms.LoginRealm;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.PrivateKey;

@Controller
public class ClearCacheController {

    @Autowired
    private LoginRealm loginRealm;

    @RequestMapping("clearShiroCache")
    public String clearShiroCache(){
        //清除缓存，将来正常开发要在service调用loginRealm.clearCached();
        loginRealm.clearCached();
        return "success";
    }
}
