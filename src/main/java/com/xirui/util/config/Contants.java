/**
 * 
 */
package com.xirui.util.config;

/**
 * <p>Title:Contants</p>
 * <p>Description:常量类</p>
 * <p>Company:北京钱袋宝公司南京分公司</p>
 * @author wanghaitao01@new4g.cn
 * @date 2016年5月13日下午2:21:20
 */
public class Contants {
	
	public final static int ONE = 1;
	
	public final static int TEN = 10;
	
	
	// 首页图片保存地址
	public final static String BANNERPATH = "banner";
	
	// 我方私钥,查询商户信息加密私钥
	public static final String daifuRSAPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALFDyR0+bIfUt2FNgjqR1LzRpoRgRdc3ftEt2mZrH31RJUZT/ua6oHoagcdk0TZoQeAMKRcw0LQqs4kMzzVPh90mW+ZemdCyOfKqtQLegjEJXMG8IXBfp0txVdkldpLKGWoWLrjlxDCPJ2u8O/X0MoI+bLqFNAsU5te/Bgu0Ne/LAgMBAAECgYAWympPEjmN9TFmlzqJITrC1Amet2oDU6VAa67KwDcaO5aZRi9dptz/Fe1dn36uihT+I1IdIy+SGnNeyYDSj9kdxOemY9BquaOAIQuPcwfJcRXPObGU6XKxbQS3+ZxxNPI7IfU4W5HKgGCAWMJK/QcNB7eVyFUMN7xW76044AZ+kQJBAOTc70L6O0En2iJBAWYG5o7P+Ri405AeC1xxnVf+d+fc2X9ZFHDOyZMnNdE1tk63C3dahC7yGaABR1/K9inwPF0CQQDGSJpGvdP3aehFA6M1DnKeDx0dpFNQu6h7wtgEgknfhO7Eo93qeOTiBiS+hoecq5ZpmNIEgR8RGtV15B0ZBtpHAkAXGMz0v3g3bSQwjMrtVwjxpL+I5S5EvyN6YuT0OnQzbDBvD7hmYhgpx61AveD2NLbthq8MZdZ8NWTUoBETznDpAkBI5+8Z7bhfc2h8376Fxp8sMMBy4rXjrGxhvR2yYrmI3xbv5JS3HuaBmOKvz0VTvN+cSh2GnHdw0Z4FL+/nANcHAkEAxSlHla7+CTCG0LBW2jvFNeSPj33BlNnqvK0SAGYojCh8U9XW8K1fYNKSDZ8iE1XMC4+50RlDciuMIrKnk7Xjnw==";
	
	// 我方私钥,查询经销商信息加密私钥
    public static final String PRIVATE_KEY_RSA = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKEPNyPD+taAXCfG6dsqnv/h7zD9SZfHaOTqoQSfr23o3ZHWL8uZzINPXGv9PYAcY6Jc1DlXxbiIJpp41rCLtolpGG1XHW44f/ZTfvx+xwQRIQbxcOqWXQYJ8HX9OMojZqK1VLNc61GzyRiAZTvx/tWYM2BciWTeB2GfOH66gRDLAgMBAAECgYBp4qTvoJKynuT3SbDJY/XwaEtmu768SF9P0GlXrtwYuDWjAVue0VhBI9WxMWZTaVafkcP8hxX4QZqPh84td0zjcq3jDLOegAFJkIorGzq5FyK7ydBoU1TLjFV459c8dTZMTu+LgsOTD11/V/Jr4NJxIudoMBQ3c4cHmOoYv4uzkQJBANR+7Fc3e6oZgqTOesqPSPqljbsdF9E4x4eDFuOecCkJDvVLOOoAzvtHfAiUp+H3fk4hXRpALiNBEHiIdhIuX2UCQQDCCHiPHFd4gC58yyCM6Leqkmoa+6YpfRb3oxykLBXcWx7DtbX+ayKy5OQmnkEG+MW8XB8wAdiUl0/tb6cQFaRvAkBhvP94Hk0DMDinFVHlWYJ3xy4pongSA8vCyMj+aSGtvjzjFnZXK4gIjBjA2Z9ekDfIOBBawqp2DLdGuX2VXz8BAkByMuIh+KBSv76cnEDwLhfLQJlKgEnvqTvXTB0TUw8avlaBAXW34/5sI+NUB1hmbgyTK/T/IFcEPXpBWLGO+e3pAkAGWLpnH0ZhFae7oAqkMAd3xCNY6ec180tAe57hZ6kS+SYLKwb4gGzYaCxc22vMtYksXHtUeamo1NMLzI2ZfUoX";
    
    
    public static final String BASE_URI = "https://s.xirui.com";
   // public static final String BASE_URI = "http://192.168.0.151:8101";

    public static final String BUY_APPLY_URI = BASE_URI + "/Jijin";
    
    public static final String BUY_RESULT_URI = BASE_URI + "/Jijin/result";
    
    public static final String QUERY_URI = BASE_URI + "/Jijin/query";
    
    public static final String INFO_URI = BASE_URI+"/MicrofinanceController/agentInfo";
    
    public static final String POINT_URI = BASE_URI + "/Jijin/points";
}
