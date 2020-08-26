package com.tw.mapper;

import com.tw.pojo.Kaoqin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KaoqinMapper {

    Kaoqin selByJobId(Integer jobid);

    long selCount(Kaoqin kaoqin);

    List<Kaoqin> selByPage(@Param("pagestart") int pagestart, @Param("pagesize") int pagesize,@Param("kaoqin")Kaoqin kaoqin);

    Kaoqin selByKaoqinInfoId(Integer jobid);

    Kaoqin selById(Integer id);

    int insertKaoqinInfo(Kaoqin kaoqin);

    int insKaoqinRecordInfo(Kaoqin kaoqin);

    Integer updateKaoqinInfoByJobIdAndMonth(Kaoqin kaoqin);

    Kaoqin selByJobIdAndMonth(Kaoqin kaoqin);

    int insertKaoqinInfoByJobIdAndMonth(Kaoqin kaoqin);

    Integer updateKaoqinApplyStatus(Kaoqin kaoqin);

    Kaoqin selKaoqinInfoByJobIdAndMonth(@Param("jobid") Integer jobid,@Param("startMonth") Integer startMonth);

    Integer insertKaoqinChuchaiInfoByJobIdAndMonth(@Param("jobid") Integer jobid,@Param("name")String name,@Param("departid")Integer departid,@Param("month") Integer startMonth,@Param("chuchaidays") Long betweenDaysFromTwoDates);

    Integer updateKaoqinChuchaiInfoByJobIdAndMonth(@Param("jobid") Integer jobid,@Param("month") Integer month,@Param("days") Long days);

    long selKaoqinRecordCount();

    List<Kaoqin> selAllKaoqinRecordByPage(int i, int limit);

    Kaoqin selKaoqinRecordById(Integer id);

    long selSelfCount(Integer jobid);

    List<Kaoqin> selSelfKaoqinInfo(@Param("pagestart") int pagestart, @Param("pagesize") int pagesize,@Param("jobid") Integer jobid);

    long selSelfKaoqinRecordCount(Integer jobid);

    List<Kaoqin> selSelfKaoqinRecord(@Param("pagestart") int pagestart, @Param("pagesize") int pagesize,@Param("jobid") Integer jobid);
}
