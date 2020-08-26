package com.tw.service;

import com.tw.pojo.WorldInfo;
import com.tw.pojo.YiQingInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface YiQingInfoService {

    int selectConfirmCountSum();
    //今日疫情分析
    List<HashMap<String,Object>> selectCurYingqing();
    //查询34个省市自治区的名称s
    List<YiQingInfo> selectPname();
    //各省的疫情分析
    List<HashMap<String,Object>> selectyiqingByPName(String name);
    //新冠型肺炎人口来源分析
    List<HashMap<String,Object>> selectCountYiqing();
    //根据用户选中省份查询该省的疫情
    List<HashMap<String,Object>> selectCountYiqingByPName(String name);
    //查询各省的前五地区确诊信息
    HashMap<String,Object> selectFiveConfrimByPName(String name);
    HashMap<String, Object> selectFiveDeadByPName(String charName);
    HashMap<String, Object> selectFiveCuredByPName(String charName);
    WorldInfo selectDeadCountOfWorld();

    List<String> selectCountryByCurrentConfirmedCount();
    List<HashMap<String, Object>> selectCurYingqingOfWorld();

    /**
     * 根据国家名称查询当日疫情数据
     * @param charName
     * @return
     */
    List<HashMap<String, Object>> selectByCountryName(String charName);


    /**
     * 加载所有国家名称
     * @return
     */
    List<YiQingInfo> selectCountryName();

    /**
     * 根据下拉框选项来加载饼图数据
     * @param charName
     * @return
     */
    List<HashMap<String, Object>> selectyiqingByCountryName(String charName);
    HashMap<String, Object> selectFiveConfrimOfWorld();
    HashMap<String, Object> selectFiveCuredOfWorld();
    HashMap<String, Object> selectFiveDeadOfWorld();

    /**
     * 折线图根据日期显示对应日期的确诊、治愈、死亡
     * @return
     */
    Map<String, Object> showYiqingOfWorldWithLine();
}
