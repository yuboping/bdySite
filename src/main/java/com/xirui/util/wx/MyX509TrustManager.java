/**
 * 
 */
package com.xirui.util.wx;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * <p>Title:MyX509TrustManager</p>
 * <p>Description:</p>
 * <p>Company:yuboping</p>
 * @author yuboping
 * @date 2016年8月23日下午8:07:58
 */
public class MyX509TrustManager implements X509TrustManager{

	// 检查客户端证书
	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		
	}

	// 检查服务器端证书
	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {		// TODO Auto-generated method stub
		
	}

	// 返回受信任的X509证书数组
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
	
}
