package com.tw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tw.pojo.Chuchai;

public interface ChuchaiMapper {

	Chuchai selByJobId(Integer jobid);

	List<Chuchai> selAll();
	
	long selCount(Chuchai chuchai);
	
	List<Chuchai> selByPage(@Param("pagestart") int pagestart, @Param("pagesize") int pagesize, @Param("name") String name,@Param("departid") Integer departid, @Param("destination") String destination,@Param("status") String status, @Param("starttime") String starttime, @Param("endtime") String endtime,@Param("jobid") Integer jobid);

	//这里需要穿id 不能传jobid  有三条记录
	Chuchai selByJobid(int jobid);

	int updateChuchaiInfo(Chuchai chuchai);
	
	int deleteChuchaiInfo(int jobid);

	int insertChuchaiInfo(Chuchai chuchai);

    Chuchai selById(Integer id);


	Chuchai selChuchaiInfoByJobIdAndStartTime(@Param("jobid") int jobid,@Param("starttime") String starttime);

	Chuchai selChuchaiInfoByJobIdAndEndTime(@Param("jobid")int jobid,@Param("endtime") String endtime);

	List<Chuchai> selChuchaiInfoByJobIdAndTime(@Param("jobid")int jobid,@Param("starttime") String starttime,@Param("endtime") String endtime);

    Integer updateChuchaiManagementStatus(Chuchai chuchai);

    Chuchai selChuchaiInfoById(Integer id);

	long selSelfCount(Integer jobid);

	List<Chuchai> selSelfChuchaiInfo(@Param("pagestart") int pagestart,@Param("pagesize") int pagesize,@Param("jobid") Integer jobid);

    Integer updateChuchaiInfoById(@Param("id") Integer id,@Param("endtime") String endtime,@Param("status")String status);

    List<Chuchai> selChuchaiInfoByNow();
}
