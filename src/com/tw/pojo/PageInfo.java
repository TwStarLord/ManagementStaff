package com.tw.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询专用
 * @author tw
 *
 */
public class PageInfo implements Serializable{

	private int pagecurrent;//当前页
	private int pagesize;//显示条数
	private int pagetotal;//总页数
	private long pagerecord;//总记录
	private List<?> list;//查询结果容器
	private int pagestart;//起始页--此属性是因为jdbc不支持模板中进行运算，需要设置好了之后直接传pageinfo
	public int getPagecurrent() {
		return pagecurrent;
	}
	public void setPagecurrent(int pagecurrent) {
		this.pagecurrent = pagecurrent;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getPagetotal() {
		return pagetotal;
	}
	public void setPagetotal(int pagetotal) {
		this.pagetotal = pagetotal;
	}
	public long getPagerecord() {
		return pagerecord;
	}
	public void setPagerecord(long pagerecord) {
		this.pagerecord = pagerecord;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public int getPagestart() {
		return pagestart;
	}
	public void setPagestart(int pagestart) {
		this.pagestart = pagestart;
	}
	@Override
	public String toString() {
		return "PageInfo [pagecurrent=" + pagecurrent + ", pagesize=" + pagesize + ", pagetotal=" + pagetotal
				+ ", pagerecord=" + pagerecord + ", list=" + list + ", pagestart=" + pagestart + "]";
	}
}
