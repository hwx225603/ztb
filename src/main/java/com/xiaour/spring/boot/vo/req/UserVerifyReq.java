package com.xiaour.spring.boot.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserVerifyReq {
		
	 @ApiModelProperty(value="姓名")
	 private String name;
	 
	 @ApiModelProperty(value="身份证号")
	 private String ident;
	 
	 @ApiModelProperty(value="认证类型 1-个人 2-企业")
	 private String type;
	 
	 @ApiModelProperty(value="公司名称")
	 private String compName;
	 
	 @ApiModelProperty(value="联系电话")
	 private String tel;
	 
	 @ApiModelProperty(value="社会信用代码")
	 private String socialCode;
	 
	 @ApiModelProperty(value="身份证正面")
	 private String identPc1;
	 
	 @ApiModelProperty(value="身份证反面")
	 private String identPc2;
	 
	 @ApiModelProperty(value="营业执照")
	 private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getSocialCode() {
		return socialCode;
	}

	public void setSocialCode(String socialCode) {
		this.socialCode = socialCode;
	}

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	 
}
