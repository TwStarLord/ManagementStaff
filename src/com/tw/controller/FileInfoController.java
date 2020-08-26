package com.tw.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tw.annotation.Operation;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.tw.pojo.PageInfo;
import com.tw.service.FileService;

@Controller
@RequestMapping("File")
public class FileInfoController {

	@Resource
	private FileService fileService;

	/**
	 * 首页点击文件上传，跳转到文件上传界面
	 */
	@RequestMapping("/ToUploadFile")
	public String ToUploadFile() {
		return "UploadFile";
	}

//	/**
//	 * 批量下载文件
//	 * @param batchfile_id
//	 * @return
//	 */
//	@RequestMapping("BatchDownloadFile")
//	@ResponseBody
//	public void BatchDownloadFile(int file_id,HttpServletRequest request,HttpServletResponse response){
//		Map<String, Object> result = new HashMap<String, Object>();
//		ServletOutputStream os = null;
//		List<File> list = new ArrayList<File>();
//		String path = request.getServletContext().getRealPath("/resources/files");
//		com.tw.pojo.File _file = fileService.selById(file_id);
//		response.setHeader("Content-Disposition", "attachment;filename="+UUID.randomUUID()+"_"+_file.getFile_name());
//	
//	}
	
	/**
	 * 首页查看所有的文件信息
	 * @return
	 */
	@RequestMapping("ToFindAllFileInfo")
    public String TofindAllFile() {
    	return "admin/AllFile_Info";
    }
	
	/**
	 * 单下载操作
	 * @param file_id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("DownloadFile")
	@ResponseBody
	public Map<String, Object> DownloadFile(Integer file_id,HttpServletRequest request,HttpServletResponse response) {

	Map<String, Object> result = new HashMap<String, Object>();
    com.tw.pojo.File _file = fileService.selById(file_id);
    //设置响应头，改变浏览器的获取文件方式（不为inline）
	response.setHeader("Content-Disposition", "attachment;filename="+UUID.randomUUID()+"_"+_file.getFile_name());
	//打印磁盘绝对路径
	String path = request.getServletContext().getRealPath("/resources/files");
	//测试是否成功
	System.out.println(path);
	File file = new File(path, _file.getFile_name());
	ServletOutputStream os = null;
	byte[] bytes = null;
	try {
		os = response.getOutputStream();
		bytes = FileUtils.readFileToByteArray(file);
		os.write(bytes);
		os.flush();
		//文件在磁盘上存在且已经成功输出了
		result.put("code", 0);
		result.put("msg", "下载成功");
		return result; 
	} catch (IOException e) {//文件不存在
		result.put("code", 1);
		result.put("msg", "下载失败!");
		return result;
	}finally {
		if(os!=null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	}
	
	/**
	 * 分页查询
	 * @param limit
	 * @param page
	 * @param file_id
	 * @return
	 */
	@RequestMapping("findAllFileInfo")
	@ResponseBody
	public Map<String, Object> findAllStaffInfo1(int limit,int page,String file_id){
		System.out.println("接收到的编号为："+file_id);
		Integer id = null;
		if(file_id!=null) {
			id = Integer.parseInt(file_id);
		}
		System.out.println("分页当前页数为："+page+"分页的每页显示记录数为"+limit+"查询的文件编号为："+file_id);
		PageInfo pi = fileService.selAllByPage(page,limit,id);
		//以下操作
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", pi.getList());
		result.put("count", pi.getPagerecord());
		result.put("msg", "");
		result.put("code", 0);
		return result;
	}
	/**
	 * 多文件上传，使用的layui插件没有多文件一起上传的功能，而是分多次请求
	 * @param file
	 * @param request
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/UploadFiles")
	@ResponseBody
	public Map<String, Object> UploadFiles(MultipartFile file,HttpServletRequest request) throws IllegalStateException, IOException {
		 System.out.println("测试有没有进入到上传控制器中：成功");
		//用来存放回调信息 
		 Map<String, Object> result = new HashMap<String, Object>();
		//获取真实路径，放在 /resources/files路径下
		 String path = request.getServletContext().getRealPath("/resources/files");
		 
         System.out.println("文件名称"+file.getOriginalFilename()); 
         
         //上传文件名         
         String name = file.getOriginalFilename();//上传文件的真实名称
         System.out.println("上传文件名为："+name);
         String suffixName = name.substring(name.lastIndexOf("."));//获取后缀名
         String hash = Integer.toHexString(new Random().nextInt());//自定义随机数（字母+数字）作为文件名
         String fileName = hash + suffixName;        
         File filepath = new File(path, fileName);
         System.out.println("随机数文件名称"+filepath.getName()); 
         //判断路径是否存在，没有就创建一个 
         if (!filepath.getParentFile().exists()) { 
             filepath.getParentFile().mkdirs(); 
             } 
         //将上传文件保存到一个目标文档中
         File tempFile = new File(path + File.separator + fileName);
         //transferTo 里面的参数必须是文件夹路径加上文件名，要不然会造成创建没有后缀的文件，默认将路径的最后一层作为文件名创建
         file.transferTo(tempFile);
         //将成功插入的文件信息记录到数据库中  file_name,file_uuid_name,file_url,file_size,file_date,file_manager
         com.tw.pojo.File file2 = new com.tw.pojo.File();
         String file_manager = (String)request.getSession().getAttribute("login_name");
         Date date = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         sdf.format(date);
         file2.setFile_name(fileName);
         file2.setFile_uuid_name(UUID.randomUUID().toString());
         file2.setFile_url("/resources/files");
         file2.setFile_date(date.toString());
         file2.setFile_size(String.valueOf(file.getSize()));
         file2.setFile_manager(file_manager);
         int index = fileService.insFileInfo(file2);
         Map<String, Object> src = new HashMap<String, Object>();
         if(index>0) {//此时表示上传文件成功
        	 src.put("src", tempFile.getPath());
             result.put("code", 0);
             result.put("msg", "");
             result.put("data", src);
             //str = "{"code": 0,"msg": "上传成功","data": {"src":""+path+fileName + ""}}";
             System.out.println(result.toString());
             return result;
         }else {//此时表示文件上传失败
        	 src.put("src", tempFile.getPath());
             result.put("code", 0);
             result.put("msg", "");
             result.put("data", src);
             return result;
         }
         
	}


	/**
	 * 删除按钮单个删除文件信息
	 * @param request
	 * @param file_id
	 * @return
	 */
	@RequestMapping("DeleteFileInfo")
	@ResponseBody
	@Operation(name = "删除文件信息")
	@RequiresPermissions("file:delete")
	public String deleteFileInfo(HttpServletRequest request,Integer file_id){

		com.tw.pojo.File fileToDeleteInfo = fileService.selFileUrlByFileId(file_id);
		Integer result = fileService.deleteBatchFile(file_id);
		try {
			File fileToDelete = new File(request.getServletContext().getRealPath(fileToDeleteInfo.getFile_url()+"/"+fileToDeleteInfo.getFile_name()));
			if (fileToDelete.exists()){
				fileToDelete.delete();
			}
		} catch (Exception e) {
			return "fail";
		}
		return result > 0 ? "success" : "fail";
	}

	/**
	 * 批量删除文件
	 * @param batchfile_id
	 * @return
	 */
	@RequestMapping("BatchDeleteFileInfo")
	@ResponseBody
	@Operation(name = "批量删除文件信息")
	@RequiresPermissions("file:batchdelete")
	public String deleteBatchFileInfo(String batchfile_id,HttpServletRequest request){
//		System.out.println(batchfile_id);
		String[] list = batchfile_id.split(",");
		boolean flag=true;
		List<com.tw.pojo.File> fileInfos = new ArrayList<>();
		Integer file_id = null;
		Integer result = null;
		for (String s : list) {
			file_id = Integer.valueOf(s);
			fileInfos.add(fileService.selFileUrlByFileId(file_id));
			result = fileService.deleteBatchFile(file_id);
			if(result <0 ){  //删除文件在数据中的信息没有成功
				flag = false;
			}
		}

		//根据文件路径删除
		File fileToDelete = null;
		for(com.tw.pojo.File fileInfo:fileInfos){
			fileToDelete = new File(request.getServletContext().getRealPath(fileInfo.getFile_url()+"/"+fileInfo.getFile_name()));
			if(fileToDelete.exists()){
				try {
					fileToDelete.delete();
				} catch (Exception e) {
					flag = false;
				}
			}
		}

		if(flag){
			return "success";
		}else{
			return "fail";
		}
	}
}
