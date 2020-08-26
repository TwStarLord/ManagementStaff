package com.tw.service.impl;

import com.tw.mapper.YiQingInfoMapper;
import com.tw.pojo.WorldInfo;
import com.tw.pojo.YiQingInfo;
import com.tw.service.YiQingInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Service
public class YiQingInfoServiceImpl implements YiQingInfoService {

    @Resource
    private YiQingInfoMapper yiQingInfoMapper;

    @Override
    public int selectConfirmCountSum() {
        //拿到墩子切的菜
        int num = yiQingInfoMapper.selectConfirmCountSum();
        return num;
    }

    /**
     * 查询国内的当日疫情情况
     */
    @Override
    public List<HashMap<String, Object>> selectCurYingqing() {
        //确诊人数
        int confirmNum = yiQingInfoMapper.selectConfirmCountSum();
        //治愈人数
        int curedNum = yiQingInfoMapper.selectCuredCountSum();
        //死亡人数
        int deadNum = yiQingInfoMapper.selectDeadCountSum();

        //创建一个集合对象
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        //构建现有确诊的数据格式
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", "现有确诊");
        map.put("value", confirmNum);
        list.add(map);

        //构建治愈人数的数据格式
        HashMap<String, Object> mapCure = new HashMap<String, Object>();
        mapCure.put("name", "现有治愈");
        mapCure.put("value", curedNum);
        list.add(mapCure);

        //构建死亡人数的数据格式
        HashMap<String, Object> mapDead = new HashMap<String, Object>();
        mapDead.put("name", "现有死亡");
        mapDead.put("value", deadNum);
        list.add(mapDead);

        return list;
    }

    /**
     *查询国外的当日疫情状况
     */
    @Override
    public List<HashMap<String, Object>> selectCurYingqingOfWorld() {
        //确诊人数
        Integer confirmNum = yiQingInfoMapper.selectConfirmCountSumOfWorld();
        //治愈人数
        Integer curedNum = yiQingInfoMapper.selectCuredCountSumOfWorld();
        //死亡人数
        Integer deadNum = yiQingInfoMapper.selectDeadCountSumOfWorld();

        //创建一个集合对象
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        //构建现有确诊的数据格式
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", "现有确诊");
        map.put("value", confirmNum);
        list.add(map);

        //构建治愈人数的数据格式
        HashMap<String, Object> mapCure = new HashMap<String, Object>();
        mapCure.put("name", "现有治愈");
        mapCure.put("value", curedNum);
        list.add(mapCure);

        //构建死亡人数的数据格式
        HashMap<String, Object> mapDead = new HashMap<String, Object>();
        mapDead.put("name", "现有死亡");
        mapDead.put("value", deadNum);
        list.add(mapDead);

        return list;
    }

    /**
     * 查询所有省名称
     */
    @Override
    public List<YiQingInfo> selectPname() {

        return yiQingInfoMapper.selectPname();
    }

    /**
     * 查询所有国家名称
     */
    @Override
    public List<YiQingInfo> selectCountryName() {

        return yiQingInfoMapper.selectCountryName();
    }



    @Override
    public List<HashMap<String, Object>> selectyiqingByPName(String name) {

//		System.out.println("省份="+name);

        YiQingInfo info =yiQingInfoMapper.selectyiqingByPName(name);
        //创建一个集合对象
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        //构建现有确诊的数据格式
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", "现有确诊");
        map.put("value", info.getConfirmCount());
        list.add(map);

        //构建治愈人数的数据格式
        HashMap<String, Object> mapCure = new HashMap<String, Object>();
        mapCure.put("name", "现有治愈");
        mapCure.put("value", info.getCuredCount());
        list.add(mapCure);

        //构建死亡人数的数据格式
        HashMap<String, Object> mapDead = new HashMap<String, Object>();
        mapDead.put("name", "现有死亡");
        mapDead.put("value", info.getDeadCount());
        list.add(mapDead);

        return list;
    }

    /**
     * 根据下拉框选项查看具体国家的疫情数据
     */
    @Override
    public List<HashMap<String, Object>> selectByCountryName(String name) {

//		System.out.println("省份="+name);

        YiQingInfo info =yiQingInfoMapper.selectyiqingByPName(name);
        //创建一个集合对象
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        //构建现有确诊的数据格式
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", "现有确诊");
        map.put("value", info.getConfirmCount());
        list.add(map);

        //构建治愈人数的数据格式
        HashMap<String, Object> mapCure = new HashMap<String, Object>();
        mapCure.put("name", "现有治愈");
        mapCure.put("value", info.getCuredCount());
        list.add(mapCure);

        //构建死亡人数的数据格式
        HashMap<String, Object> mapDead = new HashMap<String, Object>();
        mapDead.put("name", "现有死亡");
        mapDead.put("value", info.getDeadCount());
        list.add(mapDead);

        return list;
    }

    @Override
    public List<HashMap<String, Object>> selectCountYiqing() {
        //获取34个省市自治区的疫情数据
        List<YiQingInfo> list = yiQingInfoMapper.selectPname();

        //创建一个地图数据集合
        List<HashMap<String,Object>> listMap = new ArrayList<HashMap<String,Object>>();
        //构建中国地图需要的数据格式
        for(YiQingInfo info:list){
            HashMap<String,Object> map = new HashMap<String,Object> ();
            map.put("name", info.getProvinceName());
            map.put("value", info.getConfirmCount());
            listMap.add(map);
        }

        return listMap;
    }

    @Override
    public List<HashMap<String, Object>> selectCountYiqingByPName(String name) {
        //获取查询省市自治区的疫情数据
        List<YiQingInfo> list = yiQingInfoMapper.selectCountYiqingByPName(name);

        //创建一个地图数据集合
        List<HashMap<String,Object>> listMap = new ArrayList<HashMap<String,Object>>();
        //构建中国地图需要的数据格式
        for(YiQingInfo info:list){
            HashMap<String,Object> map = new HashMap<String,Object> ();
            map.put("name", info.getAreaName());
            map.put("value", info.getConfirmCount());
            listMap.add(map);
        }

        return listMap;
    }

    @Override
    public HashMap<String, Object> selectFiveConfrimByPName(String name) {

        List<YiQingInfo> list = yiQingInfoMapper.selectFiveConfrimByPName(name);
        //创建一个map对象
        HashMap<String, Object> map = new HashMap<String, Object>();
        //构建柱状图需要的数据格式
        if (null != list&& list.size()>0){
            //构建前五的地区名称
            List<String> listAreaName = new ArrayList<String>();
            for(YiQingInfo info :list){
                listAreaName.add(info.getAreaName());
                System.out.println("在这里测试数据是否获取到了："+info.toString());
            }
            map.put("area", listAreaName);

            //构建前五的确诊数据
            List<Integer> listData = new ArrayList<Integer>();
            for(YiQingInfo info :list){
                listData.add(info.getConfirmCount());
            }
            map.put("data", listData);
            //构建时间

            map.put("time", list.get(0).getTime());
        }

        return map;
    }

    @Override
    public HashMap<String, Object> selectFiveDeadByPName(String charName) {
        List<YiQingInfo> list = yiQingInfoMapper.selectFiveDeadByPName(charName);
        //创建一个map对象
        HashMap<String, Object> map = new HashMap<String, Object>();
        //构建柱状图需要的数据格式
        if (null != list&& list.size()>0){
            //构建前五的地区名称
            List<String> listAreaName = new ArrayList<String>();
            for(YiQingInfo info :list){
                listAreaName.add(info.getAreaName());
                System.out.println("在这里测试数据是否获取到了："+info.toString());
            }
            map.put("area", listAreaName);

            //构建前五的确诊数据
            List<Integer> listData = new ArrayList<Integer>();
            for(YiQingInfo info :list){
                listData.add(info.getConfirmCount());
            }
            map.put("data", listData);
            //构建时间

            map.put("time", list.get(0).getTime());
        }

        return map;
    }

    @Override
    public HashMap<String, Object> selectFiveCuredByPName(String charName) {
        List<YiQingInfo> list = yiQingInfoMapper.selectFiveCuredByPName(charName);
        //创建一个map对象
        HashMap<String, Object> map = new HashMap<String, Object>();
        //构建柱状图需要的数据格式
        if (null != list&& list.size()>0){
            //构建前五的地区名称
            List<String> listAreaName = new ArrayList<String>();
            for(YiQingInfo info :list){
                listAreaName.add(info.getAreaName());
                System.out.println("在这里测试数据是否获取到了："+info.toString());
            }
            map.put("area", listAreaName);

            //构建前五的确诊数据
            List<Integer> listData = new ArrayList<Integer>();
            for(YiQingInfo info :list){
                listData.add(info.getConfirmCount());
            }
            map.put("data", listData);
            //构建时间

            map.put("time", list.get(0).getTime());
        }

        return map;
    }

    @Override
    public WorldInfo selectDeadCountOfWorld() {
        return yiQingInfoMapper.selectDeadCountOfWorld();
    }

    @Override
    public List<String> selectCountryByCurrentConfirmedCount() {

        List<String> list = new ArrayList<String>();
        list.add(yiQingInfoMapper.selectMostSeriousCountry());
        list.add(yiQingInfoMapper.selectLeastSeriousCountry());

        return list;
    }

    /**
     * 根据下拉框选项查询具体国家的疫情数据
     */
    @Override
    public List<HashMap<String, Object>> selectyiqingByCountryName(String charName) {
        YiQingInfo info =yiQingInfoMapper.selectyiqingByCountryName(charName);
        //创建一个集合对象
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        //构建现有确诊的数据格式
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", "现有确诊");
        map.put("value", info.getConfirmCountOfCountry());
        list.add(map);

        //构建治愈人数的数据格式
        HashMap<String, Object> mapCure = new HashMap<String, Object>();
        mapCure.put("name", "现有治愈");
        mapCure.put("value", info.getCuredCountOfCountry());
        list.add(mapCure);

        //构建死亡人数的数据格式
        HashMap<String, Object> mapDead = new HashMap<String, Object>();
        mapDead.put("name", "现有死亡");
        mapDead.put("value", info.getDeadCountOfCountry());
        list.add(mapDead);

        return list;
    }

    /**
     * 世界前五确诊
     */
    @Override
    public HashMap<String, Object> selectFiveConfrimOfWorld() {
        List<WorldInfo> list = yiQingInfoMapper.selectFiveConfrimOfWorld();
        //创建一个map对象
        HashMap<String, Object> map = new HashMap<String, Object>();
        //构建柱状图需要的数据格式
        if (null != list&& list.size()>0){
            //构建前五的地区名称
            List<String> listProvinceName = new ArrayList<String>();
            for(WorldInfo info :list){
                listProvinceName.add(info.getProvinceName());
            }
            map.put("area", listProvinceName);

            //构建前五的确诊数据
            List<Integer> listData = new ArrayList<Integer>();
            for(WorldInfo info :list){
                listData.add(info.getCurrentConfirmedCount());
            }
            map.put("data", listData);
            //构建时间

            map.put("time", list.get(0).getTime());
        }

        return map;
    }


    /**
     * 世界前五治愈
     */
    @Override
    public HashMap<String, Object> selectFiveCuredOfWorld() {
        List<WorldInfo> list = yiQingInfoMapper.selectFiveCuredOfWorld();
        //创建一个map对象
        HashMap<String, Object> map = new HashMap<String, Object>();
        //构建柱状图需要的数据格式
        if (null != list&& list.size()>0){
            //构建前五的地区名称
            List<String> listProvinceName = new ArrayList<String>();
            for(WorldInfo info :list){
                listProvinceName.add(info.getProvinceName());
            }
            map.put("area", listProvinceName);

            //构建前五的确诊数据
            List<Integer> listData = new ArrayList<Integer>();
            for(WorldInfo info :list){
                listData.add(info.getCuredCount());
            }
            map.put("data", listData);
            //构建时间

            map.put("time", list.get(0).getTime());
        }

        return map;
    }


    /**
     * 世界前五死亡
     */
    @Override
    public HashMap<String, Object> selectFiveDeadOfWorld() {
        List<WorldInfo> list = yiQingInfoMapper.selectFiveDeadOfWorld();
        //创建一个map对象
        HashMap<String, Object> map = new HashMap<String, Object>();
        //构建柱状图需要的数据格式
        if (null != list&& list.size()>0){
            //构建前五的地区名称
            List<String> listProvinceName = new ArrayList<String>();
            for(WorldInfo info :list){
                listProvinceName.add(info.getProvinceName());
            }
            map.put("area", listProvinceName);

            //构建前五的确诊数据
            List<Integer> listData = new ArrayList<Integer>();
            for(WorldInfo info :list){
                listData.add(info.getDeadCount());
            }
            map.put("data", listData);
            //构建时间

            map.put("time", list.get(0).getTime());
        }

        return map;
    }

    /**
     * 折线图根据日期显示对应日期的确诊、治愈、死亡
     */
    @Override
    public Map<String, Object> showYiqingOfWorldWithLine() {
        List<String> dateList = yiQingInfoMapper.selectFiveTime();

        Collections.reverse(dateList);

        List<Integer> confirmedList = new ArrayList<Integer>();
        List<Integer> curedList = new ArrayList<Integer>();
        List<Integer> deadList = new ArrayList<Integer>();

        Integer confirmedCount = null;
        Integer curedCount = null;
        Integer deadCount = null;

        for(String date:dateList) {
            confirmedCount = yiQingInfoMapper.selectConfirmedCountByTime(date);
            confirmedList.add(confirmedCount);
            curedCount = yiQingInfoMapper.selectCuredCountByTime(date);
            curedList.add(curedCount);
            deadCount = yiQingInfoMapper.selectDeadCountByTime(date);
            deadList.add(deadCount);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("dateList", dateList);
        result.put("confirmedList", confirmedList);
        result.put("curedList", curedList);
        result.put("deadList", deadList);

        return result;
    }

}
