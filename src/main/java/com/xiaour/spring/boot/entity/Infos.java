package com.xiaour.spring.boot.entity;

import java.util.Date;

public class Infos {
    private Integer id;

    private String title;

    private String publiser;

    private String postion;

    private Date createTime;
    
    private String time;

    private String type;

    private String content;
    
    private String phone;
    

    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPubliser() {
        return publiser;
    }

    public void setPubliser(String publiser) {
        this.publiser = publiser == null ? null : publiser.trim();
    }

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion == null ? null : postion.trim();
    }

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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