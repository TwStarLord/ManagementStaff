package com.tw.service.impl;

import com.tw.mapper.NewsMapper;
import com.tw.pojo.News;
import com.tw.service.NewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NewsServiceImpl implements NewsService {

    @Resource
    private NewsMapper newsMapper;

    @Override
    public Integer insNews(News news) {

        return newsMapper.insNews(news);
    }

    @Override
    public Long getPendingNews(Integer jobid) {
        return newsMapper.getPendingNews(jobid);
    }

    @Override
    public Integer insKaoqinChangeStatusNews(News news) {
        Integer index1 = newsMapper.insNews(news);
//        news.setDescripetion(String.valueOf(news.getId()));
//        Integer index2 = newsMapper.updateNewsDescripetion(news);
//        return (index1>0 && index2>0) ? 1:0;
        return index1;
    }
}
