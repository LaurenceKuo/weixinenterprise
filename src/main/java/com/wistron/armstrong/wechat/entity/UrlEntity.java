package com.wistron.armstrong.wechat.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UrlEntity implements Serializable{
	
	private final String get_parties_url="https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN";
	private final String get_tags_url="https://qyapi.weixin.qq.com/cgi-bin/tag/list?access_token=ACCESS_TOKEN";
	// 获取access_token的接口地址（GET） 限200（次/天）
    public final String access_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=SECRETID";
    // 菜单创建（POST） 限100（次/天）
    public final String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	public final String send_msg_url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";  
    public String getSendMsgUrl() {
		return send_msg_url;
	}
    public String getGetPartiesUrl() {
		return get_parties_url;
	}
	public String getGetTagsUrl() {
		return get_tags_url;
	}
	public String getAccessTokenUrl() {
		return access_token_url;
	}
	public String getMenuCreateUrl() {
		return menu_create_url;
	}
}
