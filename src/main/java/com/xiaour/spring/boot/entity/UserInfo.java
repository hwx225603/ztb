package com.xiaour.spring.boot.entity;

import java.util.Date;

public class UserInfo {
    private Integer id;

    private String name;

    private String userName;
    
    private String passWrod;
    
    private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWrod() {
		return passWrod;
	}

	public void setPassWrod(String passWrod) {
		this.passWrod = passWrod;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
}