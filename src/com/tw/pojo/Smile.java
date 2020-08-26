package com.tw.pojo;

import java.io.Serializable;

public class Smile implements Serializable{

	private int id;
	private int id2;
	private String name;
	private int id3;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId2() {
		return id2;
	}
	public void setId2(int id2) {
		this.id2 = id2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId3() {
		return id3;
	}
	public void setId3(int id3) {
		this.id3 = id3;
	}
	@Override
	public String toString() {
		return "Smile [id=" + id + ", id2=" + id2 + ", name=" + name + ", id3=" + id3 + "]";
	}
}
