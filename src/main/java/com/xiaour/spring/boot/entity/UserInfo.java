package com.xiaour.spring.boot.entity;

import java.util.Date;

public class UserInfo {
    private Integer id;

    private String phone;

    private String passWord;

    private String name;

    private String status;

    private Date createTime;
    
    private String ident;

    private String hasVerifyP;

    private String hasVerifyC;

    private String compName;

    private String tel;

    private String socialCode;

    private String url;
    
    private String identPc1;
    
    private String identPc2;
    

    public String getIdentPc1() {
		return identPc1;
	}

	public void setIdentPc1(String identPc1) {
		this.identPc1 = identPc1;
	}

	public String getIdentPc2() {
		return identPc2;
	}

	public void setIdentPc2(String identPc2) {
		this.identPc2 = identPc2;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord == null ? null : passWord.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident == null ? null : ident.trim();
    }

    public String getHasVerifyP() {
		return hasVerifyP;
	}

	public void setHasVerifyP(String hasVerifyP) {
		this.hasVerifyP = hasVerifyP;
	}

	public String getHasVerifyC() {
		return hasVerifyC;
	}

	public void setHasVerifyC(String hasVerifyC) {
		this.hasVerifyC = hasVerifyC;
	}

	public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName == null ? null : compName.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getSocialCode() {
        return socialCode;
    }

    public void setSocialCode(String socialCode) {
        this.socialCode = socialCode == null ? null : socialCode.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}