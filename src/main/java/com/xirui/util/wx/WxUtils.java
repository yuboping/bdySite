package com.xirui.util.wx;

import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xirui.util.common.ConfigUtil;
import com.xirui.util.common.HttpUtils;
import com.xirui.util.common.StringUtil;
import com.xirui.util.config.WxReqUrl;

/**
 * 
 * <p>Title:WxUtils</p>
 * <p>Description:微信工具类</p>
 * <p>Company:yuboping</p>
 * @author yuboping
 * @date 2016年6月28日上午9:56:19
 */
public class WxUtils {
	
	public static Map<String, String> map = new HashMap<String, String>();
	public static String webAccessToken = "";// 网页授权获取的access_token
	public static Long expiresIn;// 网页授权获取的access_token的有效期
	public static String openid = "";// 用户全局唯一标识
	public static String refreshToken = "";// 网页授权刷新token
	public static String accessToken = "";
	public static Date getTokenTime = null;// 获取access_token的时间
	public static Date getJsapiTicketTime = null;
	public static Date getWebTokenTime = null;// 获取网页授权获取的access_token的时间
	public static String APPID = ConfigUtil.getPropertyKey("AppID"); 
	
	public static String APPSECRET = ConfigUtil.getPropertyKey("AppSecret");
	public static String TOKEN = ConfigUtil.getPropertyKey("token");
	public static String jsapiTicke = "";// 微信JS接口的临时票据
		
	
	/**
	 * 
	 * <p>Description:根据code获取accessToken</p>
	 * @author yuboping
	 * @date 2016年6月28日 下午5:08:12
	 * @param code
	 * @return
	 */
	public static String getAccessToken(String code) {
		String url = WxReqUrl.ACCESS_TOKEN+"?appid="+APPID.trim()+"&secret="+APPSECRET.trim()+"&code="+code.trim()
				+"&grant_type=authorization_code";
		String access = HttpUtils.getWxUrl(url,"GET");
		System.out.println("返回结果："+access);
		JSONObject js = JSON.parseObject(access);
		webAccessToken = js.getString("access_token");
		openid = js.getString("openid");
		refreshToken = js.getString("refresh_token");
		return js.toJSONString();
	}
	
	/**
	 * 
	 * <p>Description:根据openId,APPSECRET获取访问token</p>
	 * @author yuboping
	 * @date 2016年8月29日 上午10:46:27
	 * @return
	 */
	public static String getAccess_token() {
		if (StringUtils.isNotEmpty(accessToken)) { // 当前token不为空
			Date nowDate = new Date();
			Long time = (nowDate.getTime() - getTokenTime.getTime()) / 1000;
			if (time < 7200) {// 如果未超时,直接返回当前Token
				return accessToken;
			}
		} 
		String message = HttpUtils.getWxUrl(WxReqUrl.GET_ACCESS_TOKEN+"&appid=" + APPID + "&secret=" + APPSECRET,"GET");
		JSONObject js = JSON.parseObject(message);
		accessToken = js.getString("access_token");
		getTokenTime = new Date();
		return accessToken;
	}
	
	/**
	 * 
	 * <p>Description:获取OpenId</p>
	 * @author yuboping
	 * @date 2016年7月4日 上午10:29:46
	 * @param code
	 * @return
	 */
	public static String getOpenId(String code){
		getAccessToken(code);
		return openid;
	}
	
	
	/**
	 * 
	 * <p>Description:根据accessToken 和 openId 获取用户信息</p>
	 * @author yuboping
	 * @date 2016年6月30日 下午2:01:30
	 * @param token
	 * @param openId
	 * @return
	 */
	public static String getUserInfo() {
		String url = WxReqUrl.USERINFO+"?access_token="+webAccessToken+"&openid="+openid;
		return HttpUtils.getWxUrl(url,"GET");
	}
	
	/**
	 * 
	 * <p>Description:根据openId查询用户信息</p>
	 * @author yuboping
	 * @date 2016年8月25日 上午11:11:22
	 * @param openId
	 * @return
	 */
	public static String getUserInfo(String openId) {
		String token = getAccess_token(); // 重新获取access_token
		String url = WxReqUrl.GETUSER+"?access_token="+token+"&openid="+openId+"&lang=zh_CN";
		return HttpUtils.getWxUrl(url,"GET");
	}
	
	/**
	 * 
	 * <p>Description:生成 jssdk 签名signature</p>
	 * @author yuboping
	 * @date 2016年9月18日 下午5:37:54
	 * @param url
	 * @return
	 */
	public static Map<String,String> createSignature(String url,String params) {
		String ul = "";
		int endIndex = 0;
		if (url.indexOf("#") > 0) {
			endIndex = url.indexOf("#");
			ul = url.substring(0, endIndex);
		} else {
			ul = url;
		}
		
		if (StringUtils.isNotBlank(params)) {
			ul += "?" + params;
		}
		System.out.println("URL:"+ul);
		Map<String,String> result = getSign(ul);
		return result;
	}
	
	/**
	 * 
	 * <p>Description:获取signature值,封装JSAPI数据</p>
	 * @author yuboping
	 * @date 2016年9月18日 下午5:53:13
	 * @param url
	 * @return
	 */
	public static Map<String,String> getSign(String url) {
		Map<String, String> result = new HashMap<String, String>();
		String nonce_str = StringUtil.getUUIDStr();
		String timestamp = StringUtil.getTimestamp();
		String signature = "";
		String[] paramArr = new String[] { "jsapi_ticket=" + getJSapiTicke(),
				"timestamp=" + timestamp, "noncestr=" + nonce_str, "url=" + url };
		Arrays.sort(paramArr);
		
		// 注意这里参数名必须全部小写，且必须有序
		String info = paramArr[0] + "&" + paramArr[1]
				+ "&" + paramArr[2] + "&" + paramArr[3];
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(info.getBytes("UTF-8"));
			signature = byteToHex(digest.digest());
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		result.put("url", url);
		result.put("jsapi_ticket", getJSapiTicke());
		result.put("nonceStr", nonce_str);
		result.put("timestamp", timestamp);
		result.put("signature", signature);
		result.put("appid", APPID);
		return result;
	}
	
	/**
	 * 
	 * <p>Description:格式化输出字符串,ASCII</p>
	 * @author yuboping
	 * @date 2016年9月18日 下午6:01:45
	 * @param hash
	 * @return
	 */
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	
	/**
	 * 
	 * <p>Description:微信JS接口的临时票据</p>
	 * @author yuboping
	 * @date 2016年9月18日 下午5:17:59
	 * @return
	 */
	public static String getJSapiTicke() {
		Date date = new Date();
		if (getJsapiTicketTime != null) {// 上次获取的时间不为空
			Long time = (date.getTime() - getJsapiTicketTime.getTime()) / 1000;
			if (time < 7200) {
				return jsapiTicke;
			}
		}
		String url = WxReqUrl.JSAPI + "?access_token="+getAccess_token()+"&type=jsapi";
		String jsapi = HttpUtils.getWxUrl(url , "GET");
		JSONObject jsapiJson = JSON.parseObject(jsapi);
		jsapiTicke = jsapiJson.getString("ticket");
		getJsapiTicketTime = new Date();
		return jsapiTicke; 
	}
	
	/**
	 * 
	 * <p>Description:获取下载图片地址</p>
	 * @author yuboping
	 * @date 2016年9月27日 下午2:38:33
	 * @param mediaId
	 * @return
	 */
	public static InputStream getDownloadImg(String mediaId) {
		String url = WxReqUrl.IMAGE + "?access_token="+getAccess_token()+"&media_id="+mediaId;
		InputStream is = HttpUtils.getFileInput(url , "GET");	
		return is;
	}
	
	
//	/**
//	 * 
//	 * <p>Description:购买成功</p>
//	 * @author yuboping
//	 * @date 2016年8月23日 下午7:39:54
//	 */
//	public static void sendBuy(WxSale sale,WxUser user) {
//		WxTemplate t = new WxTemplate();
//		t.setUrl("");
//		t.setTouser(user.getOpenid());
//		t.setTopcolor("#000000");
//		t.setTemplate_id("dDN7fQExBJui6q9IpujiCZxen2seS9fura-HDNGu9M4");
//		Map<String,TemplateData> map = new HashMap<String,TemplateData>();
//		TemplateData first = new TemplateData();
//		first.setColor("#FF0000");
//		first.setValue("尊敬的"+user.getNickname()+"： \n"+
//				"您于"+DateUtil.formatDate(sale.getCreatedAt(), "MM月dd日")+"购买的"+sale.getProductName()+"已购买成功");
//		map.put("first", first);
//		
//		TemplateData keyword1 = new TemplateData();
//		keyword1.setColor("#173177");
//		keyword1.setValue(sale.getFtName());
//		map.put("keyword1", keyword1);
//		
//		TemplateData keyword2 = new TemplateData();
//		keyword2.setColor("#173177");
//		keyword2.setValue(sale.getOrderno());
//		map.put("keyword2", keyword2);
//		
//		TemplateData keyword3 = new TemplateData();
//		keyword3.setColor("#173177");
//		keyword3.setValue(String.valueOf(sale.getAmount().doubleValue()));
//		map.put("keyword3", keyword3);
//		
//		TemplateData keyword4 = new TemplateData();
//		keyword4.setColor("#173177");
//		keyword4.setValue(DateUtil.formatDate(sale.getCreatedAt(), "yyyy-MM-dd"));
//		map.put("keyword4", keyword4);
//		
//		TemplateData remark = new TemplateData();
//		remark.setColor("#173177");
//		remark.setValue("感谢您的支持");
//		map.put("remark", remark);
//		
//		t.setData(map);
//		
//		com.xirui.util.json.JSONObject result = HttpUtils.httpRequest(WxReqUrl.IS_TEMPLATE+"?access_token="+getAccess_token(),"POST",JSON.toJSONString(t));
//		System.out.println(result);
//	}
//	
//	/**
//	 * 
//	 * <p>Description:批量购买成功提醒</p>
//	 * @author yuboping@new4g.cn
//	 * @date 2016年9月8日上午9:35:23
//	 * @param sale
//	 * @param user
//	 */
//	public static void sendBuyBatch(Map<String, Object> params , WxUser user) {
//		WxTemplate t = new WxTemplate();
//		t.setUrl("http://njtest.xirui.net/wxInsurance/wx/in_order.do?page=1");
//		t.setTouser(user.getOpenid());
//		t.setTopcolor("#000000");
//		t.setTemplate_id("1gi90SOlMCpF3ytuUejcN8THFo90Q073pmZHKP1OcPM");
//		Map<String,TemplateData> map = new HashMap<String,TemplateData>();
//		TemplateData first = new TemplateData();
//		first.setColor("#FF0000");
//		first.setValue("尊敬的"+user.getNickname()+"： \n"+
//				"您之前购买的保险已成功，请点击查看");
//		map.put("first", first);
//		
//		TemplateData keyword1 = new TemplateData();
//		keyword1.setColor("#173177");
//		keyword1.setValue(params.get("number") + "笔");
//		map.put("keyword1", keyword1);
//		
//		TemplateData keyword2 = new TemplateData();
//		keyword2.setColor("#173177");
//		keyword2.setValue(DateUtil.formatDate((Date)params.get("buyTime"), "yyyy-MM-dd"));
//		map.put("keyword2", keyword2);
//		
//		TemplateData remark = new TemplateData();
//		remark.setColor("#173177");
//		remark.setValue("感谢您的支持");
//		map.put("remark", remark);
//		
//		t.setData(map);
//		
//		com.xirui.util.json.JSONObject result = HttpUtils.httpRequest(WxReqUrl.IS_TEMPLATE+"?access_token="+getAccess_token(),"POST",JSON.toJSONString(t));
//		System.out.println(result);
//	}
//	
//	/**
//	 * 
//	 * <p>Description:保单到期提醒</p>
//	 * @author yuboping
//	 * @date 2016年8月23日 下午7:39:37
//	 */
//	public static void sendExpire(String openId,WxSale sale) {
//		WxTemplate t = new WxTemplate();
//		t.setUrl("");
//		t.setTouser(openId);
//		t.setTopcolor("#000000");
//		t.setTemplate_id("dDN7fQExBJui6q9IpujiCZxen2seS9fura-HDNGu9M4");
//		Map<String,TemplateData> map = new HashMap<String,TemplateData>();
//		TemplateData first = new TemplateData();
//		first.setColor("#FF0000");
//		first.setValue("保单即将到期");
//		map.put("first", first);
//		
//		TemplateData keyword1 = new TemplateData();
//		keyword1.setColor("#173177");
//		keyword1.setValue(sale.getFtName());
//		map.put("keyword1", keyword1);
//		
//		TemplateData keyword2 = new TemplateData();
//		keyword2.setColor("#173177");
//		keyword2.setValue(sale.getFtName());
//		map.put("keyword2", keyword2);
//		
//		TemplateData keyword3 = new TemplateData();
//		keyword3.setColor("#173177");
//		keyword3.setValue(DateUtil.formatDate(sale.getCreatedAt(), "yyyy-MM-dd"));
//		map.put("keyword3", keyword3);
//		
//		TemplateData keyword4 = new TemplateData();
//		keyword4.setColor("#173177");
//		keyword4.setValue(DateUtil.formatDate(sale.getCreatedAt(), "yyyy-MM-dd"));
//		map.put("keyword4", keyword4);
//		
//		TemplateData remark = new TemplateData();
//		remark.setColor("#173177");
//		remark.setValue("请您留意到期时间，以便及时通知用户续保");
//		map.put("remark", remark);
//		
//		t.setData(map);
//		
//		com.xirui.util.json.JSONObject result = HttpUtils.httpRequest(WxReqUrl.IS_TEMPLATE,"POST",JSON.toJSONString(t));
//		System.out.println(result);
//	}
//	
//	
//	
//	/*public static void main(String[] args) {
//		WxSale sale = new WxSale();
//		sale.setFtName("李斯");
//		sale.setAmount(new BigDecimal(28.32));
//		sale.setCreatedAt(new Date());
//		sale.setProductName("个人账户险");
//		sale.setOrderno("PJBD201612009400E00059");
//		WxUser wx = new WxUser();
//		wx.setNickname("David");
//		sendBuy("o8_QqsyM8WxBZOvyLngg34Pz35S0",sale,wx);
//	}*/
//	 
//	public static void main(String[] args) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("number", "3");
//		params.put("buyTime", new Date());
//		WxUser user = new WxUser();
//		user.setNickname("yuboping");
//		user.setOpenid("o8_QqsyM8WxBZOvyLngg34Pz35S0");
//		sendBuyBatch(params, user);
//	}
}
