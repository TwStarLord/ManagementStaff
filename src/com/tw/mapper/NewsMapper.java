package com.tw.mapper;

import com.tw.pojo.News;

public interface NewsMapper {

    Integer insNews(News news);

    Long getPendingNews(Integer jobid);

    Integer updateNews(News news);

    Integer updateNewsDescripetion(News news);
}
