package com.tw.service;

import org.springframework.stereotype.Service;

import com.tw.pojo.File;
import com.tw.pojo.PageInfo;

@Service
public interface FileService {
	
	/**
	 * 分页查询
	 * @param page
	 * @param limit
	 * @return
	 */
	public PageInfo selAllByPage(int page, int limit, Integer file_id);

	public int insFileInfo(File file2);

	/**
	 * 批量删除操作
	 * @param file_id
	 * @return
	 */
	public Integer deleteBatchFile(Integer file_id);

	/**
	 * 单下载
	 * @param file_id
	 * @return
	 */
	public File selById(int file_id);


    File selFileUrlByFileId(Integer file_id);
}
