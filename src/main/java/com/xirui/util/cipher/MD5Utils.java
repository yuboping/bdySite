package com.xirui.util.cipher;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>Title:MD5Utils</p>
 * <p>Description:MD5加密工具类</p>
 * <p>Company:yuboping</p>
 * @author yuboping
 * @date 2016年5月25日上午11:09:46
 */
public class MD5Utils {
	
	private static final Logger logger = LoggerFactory.getLogger(MD5Utils.class);

	/**
	 * 
	 * <p>Description:获取MD5加密信息</p>
	 * @author yuboping
	 * @date 2016年5月25日 上午11:10:09
	 * @param sourceStr
	 * @return
	 */
	public static String md5(String sourceStr) {
		String md5str = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buff = md.digest(sourceStr.getBytes("utf-8"));
			md5str = bytesToHex(buff);
		} catch (Exception e) {
			logger.error("md5���ܴ���:" + e.getLocalizedMessage());
		}
		return md5str;
	}

	
	public static boolean verifyMd5(String source, String md5) {
		String md5_val = md5(source);
		return md5_val.equalsIgnoreCase(md5);
	}

	
	private static String bytesToHex(byte[] bytes) {

		StringBuffer md5str = new StringBuffer();
		int digital;
		for (int i = 0; i < bytes.length; i++) {
			digital = bytes[i];
			if (digital < 0) {
				digital += 256;
			}
			if (digital < 16) {
				md5str.append("0");
			}
			md5str.append(Integer.toHexString(digital));
		}
		return md5str.toString();
	}
}
