/**
 * 
 */
package com.xirui.util.config;

/**
 * <p>Title:WxReqUrl</p>
 * <p>Description:微信版本接口</p>
 * <p>Company:yuboping</p>
 * @author yuboping
 * @date 2016年6月28日下午3:29:53
 */
public class WxReqUrl {
	
	// 微信获取code信息,授权
	public final static String CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6546979cdeb1341e&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	
	// 根据code信息获取accessToken信息
	public final static String ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	// 刷新accessToken信息
	public final static String REFRESH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
	
	// 根据accessToken和appId获取用户信息
	public final static String USERINFO = "https://api.weixin.qq.com/sns/userinfo";
	
	// 检验授权凭证（access_token）是否有效
	public final static String IS_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/auth";

	// 模板接口
	public final static String IS_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/message/template/send";
	
	public final static String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	
	// 获取全局用户信息
	public final static String GETUSER = "https://api.weixin.qq.com/cgi-bin/user/info";
	
	// jsapi_ticket
	public final static String JSAPI ="https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	
	// 微信图片下载到本地
	public final static String IMAGE = "http://file.api.weixin.qq.com/cgi-bin/media/get";
}
