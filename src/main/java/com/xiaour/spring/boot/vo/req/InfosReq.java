package com.xiaour.spring.boot.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class InfosReq {
	
	@ApiModelProperty(value="标题")
	private String title;

	@ApiModelProperty(value="1-最新动态，2-甲方需求，3-乙方需求，4-平台担保，5-曝光台\r\n" + 
			"6-推广甲方，7-效果渠道，8-线下流量，9-市场优化，10-流量互换\r\n" + 
			"11-营销短信，12-代理加盟")
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
