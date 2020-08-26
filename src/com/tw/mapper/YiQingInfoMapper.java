package com.tw.mapper;

import com.tw.pojo.WorldInfo;
import com.tw.pojo.YiQingInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YiQingInfoMapper {

    //查询当天的34个省市自治区的肺炎累计确诊人数
    int selectConfirmCountSum();
    //查询治愈人数
    int selectCuredCountSum();
    //查询死亡人数
    int selectDeadCountSum();
    //查询34个省市自治区的名称
    List<YiQingInfo> selectPname();
    //根据用户选中省份查询该省的疫情
    YiQingInfo selectyiqingByPName(@Param("pName") String name);
    //根据省份查询该省的疫情
    List<YiQingInfo> selectCountYiqingByPName(@Param("pName") String name);
    //查询各省的前五地区确诊信息
    List<YiQingInfo> selectFiveConfrimByPName(@Param("pName") String name);
    List<YiQingInfo> selectFiveCuredByPName(String charName);
    List<YiQingInfo> selectFiveDeadByPName(String charName);
    WorldInfo selectDeadCountOfWorld();


    String selectMostSeriousCountry();

    String selectLeastSeriousCountry();

    /**
     * 以下三个方法是用来查询国外的当日疫情数据
     * @return
     */
    Integer selectConfirmCountSumOfWorld();
    Integer selectCuredCountSumOfWorld();
    Integer selectDeadCountSumOfWorld();


    /**
     * 根据国家名称查询当日疫情数据
     * @param charName
     * @return
     */
    YiQingInfo selectyiqingByCountryName(String charName);
    /**
     * 查询所有国家名称
     * @return
     */
    List<YiQingInfo> selectCountryName();


    /**
     * 以下三个方法用来查询世界疫情前五确诊、治愈、死亡信息
     * @return
     */
    List<WorldInfo> selectFiveDeadOfWorld();
    List<WorldInfo> selectFiveCuredOfWorld();
    List<WorldInfo> selectFiveConfrimOfWorld();

    /**
     * 查询出五个日期之后在将每日的确诊、治愈、死亡数据查出
     * @return
     */
    List<String> selectFiveTime();
    Integer selectConfirmedCountByTime(String date);
    Integer selectCuredCountByTime(String date);
    Integer selectDeadCountByTime(String date);
}
