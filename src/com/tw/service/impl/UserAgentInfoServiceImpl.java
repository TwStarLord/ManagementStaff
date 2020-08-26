package com.tw.service.impl;

import com.tw.mapper.UserAgentInfoMapper;
import com.tw.pojo.UserAgentInfo;
import com.tw.service.UserAgentInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserAgentInfoServiceImpl implements UserAgentInfoService {

    @Resource
    private UserAgentInfoMapper userAgentInfoMapper;

    @Override
    public Integer insUserAgentInfo(UserAgentInfo userAgentInfo) {
        return userAgentInfoMapper.insUserAgentInfo(userAgentInfo);
    }
}
