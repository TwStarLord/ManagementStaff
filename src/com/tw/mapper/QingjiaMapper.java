package com.tw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tw.pojo.Qingjia;

public interface QingjiaMapper {

	Qingjia selByJobId(Integer jobid);

	long selCount(Qingjia qingjia);

	List<Qingjia> selByPage(@Param("pagestart") int pagestart, @Param("pagesize") int pagesize, @Param("name") String name,@Param("departid") Integer departid,@Param("status") String status, @Param("starttime") String starttime, @Param("endtime") String endtime);

	Qingjia selByJobid(int jobid);

	int updateQingjiaInfo(Qingjia qingjia);

    Integer insertQingjiaInfo(Qingjia qingjia);

    Qingjia selById(Integer id);

    Integer updateQingjiaApplyStatus(Qingjia qingjia);

	Integer selJobIdByQingjiaId(Long id);

    Qingjia selQingjiaInfoById(Long id);

    long selSelfCount(Integer jobid);

	List<Qingjia> selSelfQingjiaInfo(@Param("pagestart") int pagestart,@Param("pagesize") int pagesize,@Param("jobid") Integer jobid);

	Integer updateQingjiaInfoById(@Param("id") Integer id,@Param("endtime") String endtime,@Param("status")String status);

	List<Qingjia> selQingjiaInfoByNow();
}
