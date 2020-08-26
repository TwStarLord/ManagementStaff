package com.tw.service;

import com.tw.exception.KaoqinException;
import com.tw.pojo.Kaoqin;
import com.tw.pojo.PageInfo;
import com.tw.pojo.Qingjia;

import java.text.ParseException;

public interface KaoqinService {

    PageInfo selAllByPage(int page, int limit, Kaoqin kaoqin);

    String insertkaoqinRecordInfo() throws KaoqinException, ParseException;

    Kaoqin selByKaoqinInfoId(Integer id);

    Kaoqin selById(Integer id);

    int insertKaoqinInfo(Kaoqin kaoqin);

    Integer updateKaoqinApplyStatus(Kaoqin kaoqin, Long newsid);

    PageInfo selAllKaoqinRecordByPage(int page, int limit);

    Kaoqin selKaoqinRecordById(Integer id);

    PageInfo selSelfKaoqinInfo(int page, int limit, Integer jobid);

    PageInfo selSelfKaoqinRecord(int page, int limit, Integer jobid);

    Integer deleteBatchKaoqinInfoById(Long id);

    Integer deleteKaoqinInfoById(Long id);
}
