package com.tw.service.impl;

import com.tw.mapper.LogInfoMapper;
import com.tw.pojo.LogInfo;
import com.tw.service.LogInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LogInfoServiceImpl implements LogInfoService {


    @Resource
    private LogInfoMapper logInfoMapper;

    /**
     * 系统日志记录
     * @param logInfo
     * @return
     */
    @Override
    public Integer insLogInfo(LogInfo logInfo) {
        return logInfoMapper.insLogInfo(logInfo);
    }
}
