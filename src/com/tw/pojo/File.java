package com.tw.pojo;

import java.io.Serializable;

public class File implements Serializable{

	private Integer file_id;
	private String file_name;
	private String file_uuid_name;
	private String file_url;
	private String file_size;
	private String file_date;
	private String file_manager;
	public Integer getFile_id() {
		return file_id;
	}
	public void setFile_id(Integer file_id) {
		this.file_id = file_id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_uuid_name() {
		return file_uuid_name;
	}
	public void setFile_uuid_name(String file_uuid_name) {
		this.file_uuid_name = file_uuid_name;
	}
	public String getFile_url() {
		return file_url;
	}
	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	public String getFile_date() {
		return file_date;
	}
	public void setFile_date(String file_date) {
		this.file_date = file_date;
	}
	public String getFile_manager() {
		return file_manager;
	}
	public void setFile_manager(String file_manager) {
		this.file_manager = file_manager;
	}
	@Override
	public String toString() {
		return "File [file_id=" + file_id + ", file_name=" + file_name + ", file_uuid_name=" + file_uuid_name
				+ ", file_url=" + file_url + ", file_size=" + file_size + ", file_date=" + file_date + ", file_manager="
				+ file_manager + "]";
	}
	
	
}
