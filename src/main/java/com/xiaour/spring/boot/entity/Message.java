package com.xiaour.spring.boot.entity;

import java.util.Date;

public class Message {
    private Integer id;

    private Integer userId;

    private String titile;

    private String read;

    private Date createTime;
    
    private String time;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile == null ? null : titile.trim();
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read == null ? null : read.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
    
}