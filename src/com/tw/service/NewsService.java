package com.tw.service;

import com.tw.pojo.News;

public interface NewsService {

    Integer insNews(News news);

    Long getPendingNews(Integer jobid);


    Integer insKaoqinChangeStatusNews(News news);
}
