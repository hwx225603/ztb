package com.xiaour.spring.boot.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class InfosReq {
	
	@ApiModelProperty(value="标题")
	private String title;

	@ApiModelProperty(value="1-甲方需求，2-乙方需求，3-平台担保，4-曝光台" + 
			"5-推广甲方，6-效果渠道，7-线下流量，8-市场优化，9-流量互换" + 
			"10-营销短信，11-代理加盟")
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
