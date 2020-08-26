package com.tw.pojo;

import java.io.Serializable;

public class Salary implements Serializable{

	private Long id;
	private Integer jobid;
	private String name;
	private Integer departid;
	private Department department;
	private Integer jiesuanyuefen;
	//添加数据便于查询数据    查询工资基于minjibengongzi、 maxjibengongzi之间
	private Integer minjibengongzi;
	private Integer jibengongzi;
	private Integer maxjibengongzi;
	
	private Integer jiangli;
	private Integer chengfa;
	private Integer jiabangongzi;
	private Integer kuanggonggongzi;
	private Integer qingjiagongzi;
	private Integer chuchaigongzi;
	//添加数据便于查询数据    查询工资基于minshijijiesuan、 maxshijijiesuan之间
	private Integer minshijijiesuan;
	private Integer shijijiesuan;
	private Integer maxshijijiesuan;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getJobid() {
		return jobid;
	}
	public void setJobid(Integer jobid) {
		this.jobid = jobid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDepartid() {
		return departid;
	}
	public void setDepartid(Integer departid) {
		this.departid = departid;
	}
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	public Integer getJiesuanyuefen() {
		return jiesuanyuefen;
	}
	public void setJiesuanyuefen(Integer jiesuanyuefen) {
		this.jiesuanyuefen = jiesuanyuefen;
	}
	public Integer getMinjibengongzi() {
		return minjibengongzi;
	}
	public void setMinjibengongzi(Integer minjibengongzi) {
		this.minjibengongzi = minjibengongzi;
	}
	public Integer getJibengongzi() {
		return jibengongzi;
	}
	public void setJibengongzi(Integer jibengongzi) {
		this.jibengongzi = jibengongzi;
	}
	public Integer getMaxjibengongzi() {
		return maxjibengongzi;
	}
	public void setMaxjibengongzi(Integer maxjibengongzi) {
		this.maxjibengongzi = maxjibengongzi;
	}
	public Integer getJiangli() {
		return jiangli;
	}
	public void setJiangli(Integer jiangli) {
		this.jiangli = jiangli;
	}
	public Integer getChengfa() {
		return chengfa;
	}
	public void setChengfa(Integer chengfa) {
		this.chengfa = chengfa;
	}
	public Integer getJiabangongzi() {
		return jiabangongzi;
	}
	public void setJiabangongzi(Integer jiabangongzi) {
		this.jiabangongzi = jiabangongzi;
	}
	public Integer getKuanggonggongzi() {
		return kuanggonggongzi;
	}
	public void setKuanggonggongzi(Integer kuanggonggongzi) {
		this.kuanggonggongzi = kuanggonggongzi;
	}
	public Integer getQingjiagongzi() {
		return qingjiagongzi;
	}
	public void setQingjiagongzi(Integer qingjiagongzi) {
		this.qingjiagongzi = qingjiagongzi;
	}
	public Integer getChuchaigongzi() {
		return chuchaigongzi;
	}
	public void setChuchaigongzi(Integer chuchaigongzi) {
		this.chuchaigongzi = chuchaigongzi;
	}
	public Integer getMinshijijiesuan() {
		return minshijijiesuan;
	}
	public void setMinshijijiesuan(Integer minshijijiesuan) {
		this.minshijijiesuan = minshijijiesuan;
	}
	public Integer getShijijiesuan() {
		return shijijiesuan;
	}
	public void setShijijiesuan(Integer shijijiesuan) {
		this.shijijiesuan = shijijiesuan;
	}
	public Integer getMaxshijijiesuan() {
		return maxshijijiesuan;
	}
	public void setMaxshijijiesuan(Integer maxshijijiesuan) {
		this.maxshijijiesuan = maxshijijiesuan;
	}

	@Override
	public String toString() {
		return "Salary{" +
				"jobid=" + jobid +
				", name='" + name + '\'' +
				", departid=" + departid +
				", department=" + department +
				", jiesuanyuefen=" + jiesuanyuefen +
				", minjibengongzi=" + minjibengongzi +
				", jibengongzi=" + jibengongzi +
				", maxjibengongzi=" + maxjibengongzi +
				", jiangli=" + jiangli +
				", chengfa=" + chengfa +
				", jiabangongzi=" + jiabangongzi +
				", kuanggonggongzi=" + kuanggonggongzi +
				", qingjiagongzi=" + qingjiagongzi +
				", chuchaigongzi=" + chuchaigongzi +
				", minshijijiesuan=" + minshijijiesuan +
				", shijijiesuan=" + shijijiesuan +
				", maxshijijiesuan=" + maxshijijiesuan +
				'}';
	}
}
