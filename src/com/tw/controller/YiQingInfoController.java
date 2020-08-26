package com.tw.controller;


import com.tw.pojo.WorldInfo;
import com.tw.pojo.YiQingInfo;
import com.tw.service.YiQingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("info")
public class YiQingInfoController {



    //创建一个厨师对象
    @Resource
    private YiQingInfoService service;

    /**
     * 跳转到国外疫情界面
     * @return
     */
    @RequestMapping("ToForeignYiQingInfo")
    public String toForeignYiQingInfo(){
        return "foreign";
    }

    /**
     * 跳转到国内疫情界面
     * @return
     */
    @RequestMapping("ToNativeYiQingInfo")
    public String toNativeYiQingInfo(){
        return "native";
    }

    /**
     * 折线图数据
     * @return
     */
    @RequestMapping("showYiqingOfWorldWithLine")
    @ResponseBody
    public Map<String, Object> showYiqingOfWorldWithLine() {

        return service.showYiqingOfWorldWithLine();
    }


    //各国疫情分析
    @RequestMapping("selectByCountryName")
    @ResponseBody
    public List<HashMap<String, Object>> selectByCountryName(@RequestParam("pName") String name){
        //			System.out.println("获取到的省份名称为："+name);
        String charName = "";

        try {
            charName = new String(name.getBytes("ISO-8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(charName);

        return service.selectByCountryName(charName);

    }

    /**
     * 在世界地图上展示当日疫情数据
     * @return
     */
    @RequestMapping("selectCurYingqingOfWorld")
    @ResponseBody
    public List<HashMap<String, Object>> selectCurYingqingOfWorld() {
        return service.selectCurYingqingOfWorld();
    }


    /**
     * 跳转到中国疫情地图
     * @return
     */
    @RequestMapping("ChangeIndex")
    public String ChangeIndex() {//后期加上按钮值，直接在世界与中国地图进行跳转

        return "redirect:/native.jsp";

    }

    /**
     * 查询最严重的以及最不严重的国家
     * @return
     */
    @RequestMapping("selectCountryByCurrentConfirmedCount")
    @ResponseBody
    public List<String> selectCountryByCurrentConfirmedCount(){
        return service.selectCountryByCurrentConfirmedCount();
    }


    /**
     * 查询疫情情况
     * @return
     */
    @RequestMapping("selectDeadCountOfWorld")
    @ResponseBody
    public WorldInfo selectDeadCountOfWorld() {
        return service.selectDeadCountOfWorld();
    }

    /**
     * 加载省级地图
     */
    @RequestMapping(value="getProvinceMapJson",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String getProvinceInfoByProvinceName(String provinceName, HttpServletRequest request, HttpServletResponse response) {

        String result = "";
        String Pname = "";
        try {
            Pname  = new String(provinceName.getBytes("ISO-8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        System.out.println("测试在加载省级地图时的省名称为："+Pname);
        try {
            result = readJsonData(request.getRealPath("/resources/json/"+Pname+".json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 次方法用来将json文件中的数据读取为字符串
     * @param pactFile
     * @return
     * @throws IOException
     */
    public static String readJsonData(String pactFile) throws IOException {
        // 读取文件数据
        //System.out.println("读取文件数据util");

        StringBuffer strbuffer = new StringBuffer();
        File myFile = new File(pactFile);//"D:"+File.separatorChar+"DStores.json"
        if (!myFile.exists()) {
            System.err.println("Can't Find " + pactFile);
        }
        try {
            FileInputStream fis = new FileInputStream(pactFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
            BufferedReader in  = new BufferedReader(inputStreamReader);

            String str;
            while ((str = in.readLine()) != null) {
                strbuffer.append(str);  //new String(str,"UTF-8")
            }
            in.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return strbuffer.toString();
    }


    //获取当天累计确诊人数
    @RequestMapping("selectConfirmCountSum")
    @ResponseBody
    public int selectConfirmCountSum(){

        return service.selectConfirmCountSum();

    }
    //今日疫情分析
    @RequestMapping("selectCurYingqing")
    @ResponseBody
    public List<HashMap<String, Object>> selectCurYingqing(){

        return service.selectCurYingqing();

    }

    //查询34个省市自治区名称
    @RequestMapping("selectPName")
    @ResponseBody
    public List<YiQingInfo> selectPName(){

        return service.selectPname();
    }

    //查询所有国家的名称
    @RequestMapping("selectCountryName")
    @ResponseBody
    public List<YiQingInfo> selectCountryName(){

        return service.selectCountryName();
    }

    //各省疫情分析
    @RequestMapping("selectyiqingByPName")
    @ResponseBody
    public List<HashMap<String, Object>> selectyiqingByPName(@RequestParam("pName") String name){
//		System.out.println("获取到的省份名称为："+name);
        String charName = "";

        try {
            charName = new String(name.getBytes("ISO-8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(charName);

        return service.selectyiqingByPName(charName);

    }
    //各国疫情分析
    @RequestMapping("selectyiqingByCountryName")
    @ResponseBody
    public List<HashMap<String, Object>> selectyiqingByCountryName(@RequestParam("pName") String name){
        System.out.println("获取到的省份名称为："+name);
        String charName = "";

        try {
            charName = new String(name.getBytes("ISO-8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(charName);

        return service.selectyiqingByCountryName(charName);

    }
    //新冠型肺炎人口来源分析
    @RequestMapping("selectCountYiqing")
    @ResponseBody
    public List<HashMap<String, Object>> selectCountYiqing(){

        return service.selectCountYiqing();

    }
    //获取查询省市自治区的疫情数据
    @RequestMapping("selectCountYiqingByPName")
    @ResponseBody
    public List<HashMap<String, Object>> selectCountYiqingByPName(@RequestParam("pName") String name){

        String charName = "";

        try {
            charName = new String(name.getBytes("ISO-8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return service.selectCountYiqingByPName(charName);

    }
    //查询各省的前五地区确诊信息
    @RequestMapping("selectFiveConfrimByPName")
    @ResponseBody
    public HashMap<String, Object>   selectFiveConfrimByPName(@RequestParam("pName") String name){

        String charName = "";

        try {
            charName = new String(name.getBytes("ISO-8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("接收到的柱形图请求参数为："+charName);

        HashMap<String, Object> selectFiveConfrimByPName = service.selectFiveConfrimByPName(charName);



        return  selectFiveConfrimByPName;
    }

    //查询世界的前五地区确诊信息
    @RequestMapping("selectFiveConfrimOfWorld")
    @ResponseBody
    public HashMap<String, Object>   selectFiveConfrimOfWorld(){

        HashMap<String, Object> selectFiveConfrimByPName = service.selectFiveConfrimOfWorld();
        return  selectFiveConfrimByPName;
    }

    //查询各省的前五地区治愈信息
    @RequestMapping("selectFiveCuredByPName")
    @ResponseBody
    public HashMap<String, Object>   selectFiveCuredByPName(@RequestParam("pName") String name){

        String charName = "";

        try {
            charName = new String(name.getBytes("ISO-8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("接收到的柱形图请求参数为："+charName);

        HashMap<String, Object> selectFiveCuredByPName = service.selectFiveCuredByPName(charName);



        return  selectFiveCuredByPName;
    }

    //查询世界的前五地区治愈信息
    @RequestMapping("selectFiveCuredOfWorld")
    @ResponseBody
    public HashMap<String, Object>   selectFiveCuredOfWorld(){

        HashMap<String, Object> selectFiveCuredByPName = service.selectFiveCuredOfWorld();
        return  selectFiveCuredByPName;
    }


    //查询各省的前五地区死亡信息
    @RequestMapping("selectFiveDeadByPName")
    @ResponseBody
    public HashMap<String, Object>   selectFiveDeadByPName(@RequestParam("pName") String name){

        String charName = "";

        try {
            charName = new String(name.getBytes("ISO-8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("接收到的柱形图请求参数为："+charName);

        HashMap<String, Object> selectFiveDeadByPName = service.selectFiveDeadByPName(charName);



        return  selectFiveDeadByPName;
    }

    //查询世界的前五地区死亡信息
    @RequestMapping("selectFiveDeadOfWorld")
    @ResponseBody
    public HashMap<String, Object>   selectFiveDeadOfWorld(){

        HashMap<String, Object> selectFiveDeadByPName = service.selectFiveDeadOfWorld();
        return  selectFiveDeadByPName;
    }

}
