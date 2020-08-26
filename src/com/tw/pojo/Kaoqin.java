package com.tw.pojo;

public class Kaoqin {

    private Long id;
    private Long jobid;
    private String name;
    private Integer departid;
    private Department department;
//    这里尝试使用字符串的格式保存数据，看看mysql会不会自动将数据转化为datetime格式。
    private String recordtime;
    private Integer checkstatus;
    private Integer month;
    private Integer zhengchangshangban;
    private Integer kuanggongday;
    private Integer qingjiaday;
    private Integer chuchaiday;

    public Kaoqin() {
    }

    public Kaoqin(Long id, Long jobid, String name, Integer departid, Department department, String recordtime, Integer checkstatus, Integer month, Integer zhengchangshangban, Integer kuanggongday, Integer qingjiaday, Integer chuchaiday) {
        this.id = id;
        this.jobid = jobid;
        this.name = name;
        this.departid = departid;
        this.department = department;
        this.recordtime = recordtime;
        this.checkstatus = checkstatus;
        this.month = month;
        this.zhengchangshangban = zhengchangshangban;
        this.kuanggongday = kuanggongday;
        this.qingjiaday = qingjiaday;
        this.chuchaiday = chuchaiday;
    }

    /**
     * 记录打卡信息
     * @param jobid
     * @param name
     * @param recordtime
     * @param checkstatus
     */
    public Kaoqin(Long jobid,String name,  String recordtime, Integer checkstatus,Integer month) {
        this.jobid = jobid;
        this.name = name;
        this.recordtime = recordtime;
        this.checkstatus = checkstatus;
        this.month = month;
    }

    public Kaoqin(Long jobid, Integer month, Integer zhengchangshangban, Integer kuanggongday) {
        this.jobid = jobid;
        this.month = month;
        this.zhengchangshangban = zhengchangshangban;
        this.kuanggongday = kuanggongday;
    }

    public Kaoqin(Long jobid, String name, Integer departid, Integer month, Integer zhengchangshangban, Integer kuanggongday, Integer qingjiaday, Integer chuchaiday) {
        this.jobid = jobid;
        this.name = name;
        this.departid = departid;
        this.month = month;
        this.zhengchangshangban = zhengchangshangban;
        this.kuanggongday = kuanggongday;
        this.qingjiaday = qingjiaday;
        this.chuchaiday = chuchaiday;
    }

    public Kaoqin(Long jobid, Integer month, Integer zhengchangshangban, Integer kuanggongday, Integer qingjiaday, Integer chuchaiday) {
        this.jobid = jobid;
        this.month = month;
        this.zhengchangshangban = zhengchangshangban;
        this.kuanggongday = kuanggongday;
        this.qingjiaday = qingjiaday;
        this.chuchaiday = chuchaiday;
    }

    public Kaoqin(long jobid, Integer month, Integer zhengchangshangban) {
        this.jobid = jobid;
        this.month = month;
        this.zhengchangshangban = zhengchangshangban;
    }

    public Kaoqin(Long jobid, Integer month) {
        this.jobid = jobid;
        this.month = month;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobid() {
        return jobid;
    }

    public void setJobid(Long jobid) {
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

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }

    public Integer getCheckstatus() {
        return checkstatus;
    }

    public void setCheckstatus(Integer checkstatus) {
        this.checkstatus = checkstatus;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getZhengchangshangban() {
        return zhengchangshangban;
    }

    public void setZhengchangshangban(Integer zhengchangshangban) {
        this.zhengchangshangban = zhengchangshangban;
    }



    public Integer getQingjiaday() {
        return qingjiaday;
    }

    public void setQingjiaday(Integer qingjiaday) {
        this.qingjiaday = qingjiaday;
    }

    public Integer getChuchaiday() {
        return chuchaiday;
    }

    public void setChuchaiday(Integer chuchaiday) {
        this.chuchaiday = chuchaiday;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getKuanggongday() {
        return kuanggongday;
    }

    public void setKuanggongday(Integer kuanggongday) {
        this.kuanggongday = kuanggongday;
    }

    @Override
    public String toString() {
        return "Kaoqin{" +
                "id=" + id +
                ", jobid=" + jobid +
                ", name='" + name + '\'' +
                ", departid=" + departid +
                ", department=" + department +
                ", recordtime='" + recordtime + '\'' +
                ", checkstatus=" + checkstatus +
                ", month=" + month +
                ", zhengchangshangban=" + zhengchangshangban +
                ", kuanggongday=" + kuanggongday +
                ", qingjiaday=" + qingjiaday +
                ", chuchaiday=" + chuchaiday +
                '}';
    }
}
