/**
 * 
 */
package com.xirui.util.common;

import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Title:PathUtils
 * </p>
 * <p>
 * Description:路径工具类
 * </p>
 * <p>
 * Company:yuboping
 * </p>
 * 
 * @author yuboping
 * @date 2016年7月15日下午2:44:50
 */
public class PathUtils {
	
	/**
	 * 
	 * <p>Description:根据request获取项目路径</p>
	 * @author yuboping
	 * @date 2016年7月15日 下午2:46:23
	 * @param path
	 * @return
	 */
	public static String getPath(HttpServletRequest request,String fileName) {
		String path = request.getSession().getServletContext().getRealPath("/");
		if (!path.endsWith(java.io.File.separator)) {
			path =  path + java.io.File.separator + fileName;
		} else {
			path =  path + fileName;
		}
 		return path;
	}
	
	/**
	 * 
	 * <p>Description:根据类获取项目路径</p>
	 * @author yuboping
	 * @date 2016年7月19日 下午4:22:59
	 * @return
	 */
	public static String getClassPath() {
		String path = "";
		URL url = PathUtils.class.getClass().getResource("/");
		if (null == url) {
			path = PathUtils.class.getClassLoader().getResource("/").getPath().substring(1);
			if (path.contains("WEB-INF")) {
				int i = path.indexOf("WEB-INF");
				path = path.substring(0, i);
			} 
		} else {
			path = url.getPath().substring(1);
		}
		try {
			path = java.net.URLDecoder.decode(path, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return java.io.File.separator + path;
	}
	
	public static void main(String[] args) {
		getClassPath();
	}
}
