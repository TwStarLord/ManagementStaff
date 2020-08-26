package com.tw.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tw.mapper.FileMapper;
import com.tw.pojo.File;
import com.tw.pojo.PageInfo;
import com.tw.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Resource
	private FileMapper fileMapper;
	@Override
	public PageInfo selAllByPage(int page, int limit,Integer file_id) {
        PageInfo pageinfo = new PageInfo();
		
		long count = fileMapper.selCount(file_id);
		
		pageinfo.setPagerecord(count);//设置总记录数
		
		pageinfo.setPagetotal((int)(count%limit==0?count/limit:count/limit+1));//设置总页数
		
		pageinfo.setPagecurrent(page);
		pageinfo.setPagesize(limit);
		
		pageinfo.setPagestart((page-1)*limit);
		
		pageinfo.setList(fileMapper.selByPage(pageinfo.getPagestart(),pageinfo.getPagesize(),file_id));//存储当页记录
		
		System.out.println("service中"+pageinfo.getPagerecord());
		
		return pageinfo;
	}
	/**
	 * 上传成功之后插入文件记录
	 */
	@Override
	public int insFileInfo(File file2) {
		return fileMapper.insFileInfo(file2);
	}
	/**
	 * 批量删除文件
	 */
	@Override
	public Integer deleteBatchFile(Integer file_id) {
		return fileMapper.deleteBatchFile(file_id);
	}
	@Override
	public File selById(int file_id) {
		return fileMapper.selById(file_id);
	}

	@Override
	public File selFileUrlByFileId(Integer file_id) {
		return fileMapper.selFileUrlByFileId(file_id);
	}

}
