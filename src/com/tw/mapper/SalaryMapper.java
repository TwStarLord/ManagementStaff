package com.tw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.tw.pojo.Salary;

public interface  SalaryMapper {

	long selCount(Salary salary);

	List<Salary> selByPage(@Param("pagestart") Integer pagestart, @Param("pagesize") Integer pagesize, @Param("name") String name,@Param("departid") Integer departid,
                           @Param("jiesuanyuefen") Integer jiesuanyuefen, @Param("minjibengongzi") Integer minjibengongzi,
                           @Param("maxjibengongzi") Integer maxjibengongzi, @Param("jiangli") Integer jiangli, @Param("chengfa") Integer chengfa,
                           @Param("jiabangongzi") Integer jiabangongzi, @Param("kuanggonggongzi") Integer kuanggonggongzi, @Param("qingjiagongzi") Integer qingjiagongzi,
                           @Param("chuchaigongzi") Integer chuchaigongzi, @Param("minshijijiesuan") Integer minshijijiesuan, @Param("maxshijijiesuan") Integer maxshijijiesuan
    );

	Salary selByJobId(Integer jobid);

    Integer insertSalaryInfo(Salary salary);

    Integer updateSalaryInfo(Salary salary);


//	Qingjia selByJobid(Integer jobid);
//
//	Integer updateQingjiaInfo(Qingjia qingjia);
}
