package com.sahara.camel.widget.sortlistview;

import java.io.Serializable;

public class SortModel implements Serializable {
	
	private static final long serialVersionUID = 1063211135430057012L;
	
	private String name;//��ʾ������
	private String phone;
	private String sortLetters;//��ʾ����ƴ��������ĸ
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
	
	
	

}
