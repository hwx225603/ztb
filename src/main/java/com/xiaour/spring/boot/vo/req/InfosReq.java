package com.xiaour.spring.boot.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class InfosReq {
	
	@ApiModelProperty(value="标题")
	private String title;

	@ApiModelProperty(value="1-最新动态，2-甲方需求，3-乙方需求，4-平台担保，5-曝光台")
    private String type;

	@ApiModelProperty(value="内容")
    private String content;
    

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
    
}
