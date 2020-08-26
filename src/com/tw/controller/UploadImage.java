package com.tw.controller;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadImage {

	@RequestMapping(value="/upload/geturl",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> uploadimage(@RequestParam("file")MultipartFile image,HttpServletRequest request) {
		
		System.out.println("进来了");
		System.out.println(request.getServletContext().getContextPath());
		System.out.println(image==null);
		String prefix="";
        String dateStr="";
        //保存上传
        OutputStream out = null;
        InputStream fileInput=null;
        try{
            if(image!=null){
                String originalName = image.getOriginalFilename();
                System.out.println("要走了");
                prefix=originalName.substring(originalName.lastIndexOf(".")+1);
                String uuid = UUID.randomUUID()+"";
                String filepath = request.getServletContext().getRealPath("/resources/images") + "/" + uuid+"." + prefix;
                

                File files = new File(filepath);
                System.out.println(files.getAbsolutePath());//我擦，这里找了半天，原来还是以磁盘E根路径为准储存图片的，要使用项目在磁盘的绝对路径
                //打印查看上传路径
                System.out.println(filepath);
                if(!files.getParentFile().exists()){
                    files.getParentFile().mkdirs();
                }
                image.transferTo(files);
                Map<String,Object> map2=new HashMap<>();
                Map<String,Object> map=new HashMap<>();
                map.put("code",0);
                map.put("msg","");
                map.put("data",map2);
                map2.put("src","resources/images/"+uuid+"." + prefix);
                return map;
            }

        }catch (Exception e){
        }finally{
            try {
                if(out!=null){
                    out.close();
                }
                if(fileInput!=null){
                    fileInput.close();
                }
            } catch (IOException e) {
            }
        }
        Map<String,Object> map=new HashMap<>();
        map.put("code",1);
        map.put("msg","");
        return map;
		
		
	}
}
