/**
 * 
 */
package com.xirui.util.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * Title:ImageUtils
 * </p>
 * <p>
 * Description:图片上传工具类
 * </p>
 * <p>
 * Company:yuboping
 * </p>
 * 
 * @author yuboping
 * @date 2016年5月12日下午3:21:31
 */
public class ImageUtils {

	private final static Logger logger = LoggerFactory.getLogger(ImageUtils.class);

	/**
	 * 
	 * <p>
	 * Description:上传图片
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月12日 下午3:33:41
	 * @param newFileName
	 *            文件名称
	 * @param path
	 *            上传路径
	 * @param filedata
	 *            文件数据
	 * @return
	 */
	public static String uploadImage(String newFileName, String path, String type, MultipartFile filedata) {
		File fileDir = new File(path);

		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(path + java.io.File.separator + newFileName);
			// 写入文件
			out.write(filedata.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("图片上传失败：" + e + " 图片路径：" + path);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("图片上传失败流关闭异常：" + e);
				}
			}
		}

		return ConfigUtil.getPropertyKey("img.path") + type + java.io.File.separator + newFileName;
	}

	/**
	 * 
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年9月29日 下午3:59:42
	 * @param newFileName
	 *            文件名称
	 * @param path
	 *            上传路径
	 * @param type
	 *            上传文件夹名称
	 * @param is
	 *            流信息
	 * @return
	 */
	public static String uploadImage(String newFileName, String path, String type, InputStream is) {
		File fileDir = new File(path);

		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		FileOutputStream out = null;
		byte[] data = new byte[1024];
		int len = 0;
		try {
			out = new FileOutputStream(path + java.io.File.separator + newFileName);
			// 写入文件
			while ((len = is.read(data)) != -1) {
				out.write(data, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("图片上传失败：" + e + " 图片路径：" + path);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("图片上传失败流关闭异常：" + e);
				}
			}
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("图片上传失败流关闭异常：" + e);
				}
			}
		}

		return ConfigUtil.getPropertyKey("img.path") + type + java.io.File.separator + newFileName;
	}

	public static boolean uploadFtpImage(String newFileName, String path, MultipartFile filedata) {
		boolean falg = false;
		try {
			falg = FTPUtil.ftpUpload(filedata.getInputStream(), newFileName, path);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("FTP图片上传失败：" + e);
		} finally {

		}
		return falg;
	}

	/**
	 * 
	 * <p>
	 * Description:删除上传图片
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月12日 下午3:37:33
	 * @param path
	 * @return
	 */
	public boolean deleteImage(String path) {
		boolean result = false;
		File fileDir = new File(path);
		if (fileDir.exists()) {
			result = fileDir.delete();
		}
		return result;
	}
}
