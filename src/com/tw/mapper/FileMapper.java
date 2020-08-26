package com.tw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tw.pojo.File;
import com.tw.pojo.PageInfo;

public interface FileMapper {

	/**
	 * 文件查询所有记录数
	 * @return
	 */
	long selCount(@Param("file_id") Integer file_id);
	
	/**
	 * 查询分页记录数，由limit限制
	 * @param pageInfo
	 * @return
	 */
	List<File> selByPage(@Param("pagestart") int pagestart, @Param("pagesize") int pagesize, @Param("file_id") Integer file_id);

	/**
	 * 上传文件成功时执行此方法，用来记录文件信息
	 * @param file2
	 * @return
	 */
	int insFileInfo(File file2);

	/**
	 * 批量删除文件操作
	 * @param file_id
	 * @return
	 */
	Integer deleteBatchFile(Integer file_id);

	/**
	 * 单下载操作
	 * @param file_id
	 * @return
	 */
	File selById(int file_id);

    File selFileUrlByFileId(Integer file_id);
}
