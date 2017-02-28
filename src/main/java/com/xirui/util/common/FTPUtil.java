/**
 * 
 */
package com.xirui.util.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>
 * Title:FTPUtil
 * </p>
 * <p>
 * Description:FTP工具类
 * </p>
 * <p>
 * Company:yuboping
 * </p>
 * 
 * @author yuboping
 * @date 2016年5月12日下午3:22:37
 */
public class FTPUtil {

	private final static Logger logger = LoggerFactory.getLogger(FTPUtil.class);

	private static final String FTPURL = ConfigUtil.getPropertyKey("ftp.url");

	private static final String FTPUSER = ConfigUtil.getPropertyKey("ftp.user");

	private static final String FTPPASSWORD = ConfigUtil
			.getPropertyKey("ftp.password");

	private static final String FTPPATH = ConfigUtil.getPropertyKey("ftp.path");

	private static final int FTPPORT = Integer.valueOf(ConfigUtil
			.getPropertyKey("ftp.port"));

	public static boolean ftpUploadToStr(String fileName, String ftpPath,
			String content) {
		FTPClient client = new FTPClient();
		boolean falg = true;
		InputStream is = null;
		try {
			client.connect(FTPURL, FTPPORT);

			client.login(FTPUSER, FTPPASSWORD);

			decodeDir(client, FTPPATH + ftpPath);

			is = new ByteArrayInputStream(content.getBytes("UTF-8"));

			client.setBufferSize(1024);

			client.setControlEncoding("GBK");

			client.setFileType(FTPClient.BINARY_FILE_TYPE);

			falg = client.storeFile(fileName, is);

			client.logout();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("File upload failed:" + e.getMessage());
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("File upload failed:" + e.getMessage());
				}
			}
			try {
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("File upload failed:" + e.getMessage());
			}
		}
		return falg;
	}

	public static boolean ftpUpload(String path, String fileName, String ftpPath) {
		FTPClient client = new FTPClient();
		File file = null;
		FileInputStream fis = null;
		boolean falg = true;

		try {
			file = new File(path);

			fis = new FileInputStream(file);
			client.connect(FTPURL, FTPPORT);

			client.login(FTPUSER, FTPPASSWORD);

			decodeDir(client, FTPPATH + ftpPath);

			client.setBufferSize(1024);
			client.setControlEncoding("GBK");
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			falg = client.storeFile(fileName, fis);
			client.logout();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("File upload failed:" + e.getMessage());
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("File upload failed:" + e.getMessage());
				}
			}
			try {
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("File upload failed:" + e.getMessage());
			}
		}
		return falg;
	}

	public static boolean ftpUpload(InputStream is, String fileName,
			String ftpPath) {

		FTPClient client = new FTPClient();

		boolean falg = true;

		try {

			client.connect(FTPURL, FTPPORT);

			client.login(FTPUSER, FTPPASSWORD);

			decodeDir(client, ftpPath);

			client.setBufferSize(1024);
			client.setControlEncoding("GBK");
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			falg = client.storeFile(fileName, is);
			client.logout();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("File upload failed:" + e.getMessage());
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("File upload failed:" + e.getMessage());
				}
			}
			try {
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("File upload failed:" + e.getMessage());
			}
		}
		return falg;
	}

	public static boolean ftpDownload(String outPath, String fileName) {
		FTPClient client = new FTPClient();
		FileOutputStream fos = null;
		boolean falg = true;

		try {
			client.connect(FTPURL, FTPPORT);
			client.login(FTPUSER, FTPPASSWORD);
			fos = new FileOutputStream(outPath);
			client.setBufferSize(1024);
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			falg = client.retrieveFile(FTPPATH + fileName, fos);
			client.logout();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("File download failed:" + e.getMessage());
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("File download failed:" + e.getMessage());
				}
			}
			try {
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("File download failed:" + e.getMessage());
			}
		}

		return falg;
	}

	public static String ftpDownloadToStr(String outPath, String code) {
		FTPClient client = new FTPClient();
		InputStream is = null;
		BufferedReader br = null;
		StringBuffer result = new StringBuffer();
		try {

			client.connect(FTPURL, FTPPORT);
			client.login(FTPUSER, FTPPASSWORD);
			is = client.retrieveFileStream(outPath);
			br = new BufferedReader(new InputStreamReader(is, code));

			String line = "";
			while ((line = br.readLine()) != null) {
				result.append(line + "\r\n");
			}

			client.logout();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("File downloadToStr failed:" + e.getMessage());
		} finally {
			if (null != is) {
				try {
					is.close();
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("File downloadToStr failed:" + e.getMessage());
				}
			}

			try {
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("File downloadToStr failed:" + e.getMessage());
			}
		}

		return result.toString();
	}

	private static boolean decodeDir(FTPClient client, String path) {
		boolean result = true;
		try {
			boolean dir = client.changeWorkingDirectory(path);
			if (!dir) {
				boolean falg = client.makeDirectory(path);
				if (!falg) {
					result = false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Failed to create directory" + e.getMessage());
		}
		return result;
	}
}
