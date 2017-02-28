package com.xirui.util.sms;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>
 * Title:MailUtils
 * </p>
 * <p>
 * Description:邮件发送工具类
 * </p>
 * <p>
 * Company:yuboping
 * </p>
 * 
 * @author yuboping
 * @date 2016年5月10日下午3:01:46
 */
public class MailUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(MailUtils.class);

	private MimeMessage mimeMsg;
	private Session session;
	private Properties props;
	private String username;
	private String password;
	private Multipart mp;

	/**
	 * 
	 * @param smtp
	 */
	public MailUtils(String smtp) {
		setSmtpHost(smtp);
		createMimeMessage();
	}

	/**
	 * 
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月10日 下午3:04:39
	 * @param hostName
	 */
	public void setSmtpHost(String hostName) {
		if (props == null)
			props = System.getProperties(); //
		props.put("mail.smtp.host", hostName); //
	}

	/**
	 * 
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月10日 下午3:04:55
	 * @return
	 */
	public boolean createMimeMessage() {
		try {
			session = Session.getDefaultInstance(props, null); // ����ʼ��Ự����
		} catch (Exception e) {
			logger.error("创建邮件发送地址:" + e);
			return false;
		}

		try {
			mimeMsg = new MimeMessage(session); //
			mp = new MimeMultipart();
			return true;
		} catch (Exception e) {
			logger.error("邮件session创建失败:" + e);
			return false;
		}
	}

	/**
	 * 
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月10日 下午3:05:02
	 * @param need
	 */
	public void setNeedAuth(boolean need) {
		if (props == null)
			props = System.getProperties();
		if (need) {
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}
	}

	/**
	 * 
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月10日 下午3:05:09
	 * @param name
	 * @param pass
	 */
	public void setNamePass(String name, String pass) {
		username = name;
		password = pass;
	}

	/**
	 * 
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月10日 下午3:05:14
	 * @param mailSubject
	 * @return
	 */
	public boolean setSubject(String mailSubject) {
		try {
			mimeMsg.setSubject(mailSubject);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("设置主题失败：" + e);
			return false;
		}
	}

	/**
	 * 
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月10日 下午3:05:21
	 * @param mailBody
	 * @return
	 */
	public boolean setBody(String mailBody) {
		try {
			BodyPart bp = new MimeBodyPart();
			bp.setContent("" + mailBody, "text/html;charset=GBK");
			mp.addBodyPart(bp);
			return true;
		} catch (Exception e) {
			logger.error("设置主题内容错误" + e);
			return false;
		}
	}

	/**
	 * 
	 * <p>
	 * Description:添加附件
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月10日 下午3:05:28
	 * @param filename
	 * @return
	 */
	public boolean addFileAffix(String filename, String name) {
		try {
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			System.out.println("附件名称：" + name);
			bp.setFileName(MimeUtility.encodeText(name));
			mp.addBodyPart(bp);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加附件失败：附件名称是：" + filename + e);
			return false;
		}
	}

	/**
	 * 
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月10日 下午3:05:34
	 * @param from
	 * @return
	 */
	public boolean setFrom(String from) {
		try {
			mimeMsg.setFrom(new InternetAddress(from)); // ���÷�����
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("设置发送人失败：" + e);
			return false;
		}
	}

	/**
	 * 
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月10日 下午3:05:40
	 * @param to
	 * @return
	 */
	public boolean setTo(String to) {
		if (to == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月10日 下午3:05:45
	 * @param copyto
	 * @return
	 */
	public boolean setCopyTo(String copyto) {
		if (copyto == null)
			return false;
		try {
			if (copyto.contains(";")) {
				String[] Cc = copyto.split(";");
				Address[] add = new InternetAddress[Cc.length];
				;
				for (int i = 0; i < Cc.length; i++) {
					add[i] = new InternetAddress(Cc[i]);
				}
				mimeMsg.setRecipients(Message.RecipientType.CC, add);
			} else {
				mimeMsg.setRecipients(Message.RecipientType.CC,
						(Address[]) InternetAddress.parse(copyto));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("抄送报错：" + e);
			return false;
		}
	}

	/**
	 * 
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月10日 下午3:05:53
	 * @param isCopyto
	 * @return
	 */
	public boolean sendOut(boolean isCopyto) {
		try {
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();

			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username,
					password);
			transport.sendMessage(mimeMsg,
					mimeMsg.getRecipients(Message.RecipientType.TO));
			if (isCopyto) {
				transport.sendMessage(mimeMsg,
						mimeMsg.getRecipients(Message.RecipientType.CC));
			}

			// transport.send(mimeMsg);

			transport.close();
			return true;
		} catch (Exception e) {
			logger.error("邮件发送失败:" + e);
			return false;
		}
	}

	/**
	 * 
	 * <p>
	 * Description:发送邮件
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月10日 下午3:06:37
	 * @param smtp
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean send(String smtp, String from, String to,
			String subject, String content, String username, String password) {
		MailUtils theMail = new MailUtils(smtp);
		theMail.setNeedAuth(true);

		if (!theMail.setSubject(subject)) {
			return false;
		}

		if (!theMail.setBody(content)) {
			return false;
		}

		if (!theMail.setTo(to)) {
			return false;
		}

		if (!theMail.setFrom(from)) {
			return false;
		}

		theMail.setNamePass(username, password);

		if (!theMail.sendOut(false)) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * <p>
	 * Description:发送邮件,代抄送
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月10日 下午3:08:03
	 * @param smtp
	 * @param from
	 * @param to
	 * @param copyto
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean sendAndC(String smtp, String from, String to,
			String copyto, String subject, String content, String username,
			String password) {
		MailUtils theMail = new MailUtils(smtp);
		theMail.setNeedAuth(true);

		if (!theMail.setSubject(subject)) {
			return false;
		}

		if (!theMail.setBody(content)) {
			return false;
		}

		if (!theMail.setTo(to)) {
			return false;
		}

		if (!theMail.setCopyTo(copyto)) {
			return false;
		}

		if (!theMail.setFrom(from)) {
			return false;
		}

		theMail.setNamePass(username, password);

		if (!theMail.sendOut(true))
			return false;
		return true;
	}

	/**
	 * 
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月10日 下午3:09:34
	 * @param smtp
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @param filename
	 * @return
	 */
	public static boolean send(String smtp, String from, String to,
			String subject, String content, String username, String password,
			String filename, String path) {
		MailUtils theMail = new MailUtils(smtp);
		theMail.setNeedAuth(true);

		if (!theMail.setSubject(subject)) {
			return false;
		}

		if (!theMail.setBody(content)) {
			return false;
		}

		if (!theMail.addFileAffix(path, filename)) {
			return false;
		}

		if (!theMail.setTo(to)) {
			return false;
		}

		if (!theMail.setFrom(from)) {
			return false;
		}

		theMail.setNamePass(username, password);

		if (!theMail.sendOut(false))
			return false;
		return true;
	}

	/**
	 * 
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月10日 下午3:11:36
	 * @param smtp
	 * @param from
	 * @param to
	 * @param copyto
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @param filename
	 * @return
	 */
	public static boolean sendAndC(String smtp, String from, String to,
			String copyto, String subject, String content, String username,
			String password, String filename, String path) {
		MailUtils theMail = new MailUtils(smtp);
		theMail.setNeedAuth(true); //

		if (!theMail.setSubject(subject)) {
			return false;
		}

		if (!theMail.setBody(content)) {
			return false;
		}

		if (!theMail.addFileAffix(path, filename)) {
			return false;
		}

		if (!theMail.setTo(to)) {
			return false;
		}

		if (!theMail.setCopyTo(copyto)) {
			return false;
		}

		if (!theMail.setFrom(from)) {
			return false;
		}

		theMail.setNamePass(username, password);

		if (!theMail.sendOut(true)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年5月10日 下午3:11:15
	 * @param smtp
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean sendAndC(String smtp, String from, String to,
			String subject, String content, String username, String password) {
		MailUtils theMail = new MailUtils(smtp);
		theMail.setNeedAuth(true);

		if (!theMail.setSubject(subject)) {
			return false;
		}

		if (!theMail.setBody(content)) {
			return false;
		}

		if (!theMail.setTo(to)) {
			return false;
		}

		if (!theMail.setFrom(from)) {
			return false;
		}

		theMail.setNamePass(username, password);

		if (!theMail.sendOut(false)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * <p>
	 * Description:发送邮件,代抄送
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016��5��6������10:22:56
	 * @param to
	 * @param copyto
	 * @param subject
	 * @param content
	 * @return
	 */
	public static boolean sendToc(String to, String copyto, String subject,
			String content) {
		String smtp = "smtp.exmail.qq.com"; // SMTP
		String from = "yuboping";
		String username = "yuboping";
		String password = "Wht007521";
		return sendAndC(smtp, from, to, copyto, subject, content, username,
				password);
	}

	/**
	 * 
	 * <p>
	 * Description:发送邮件,代抄送,代附件
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016��5��6������10:23:21
	 * @param to
	 * @param copyto
	 * @param subject
	 * @param content
	 * @param fileName
	 * @return
	 */
	public static boolean sendToc(String to, String copyto, String subject,
			String content, String fileName, String path) {
		String smtp = "smtp.exmail.qq.com"; // SMTP
		String from = "yuboping";
		String username = "yuboping";
		String password = "Wht007521";
		return sendAndC(smtp, from, to, copyto, subject, content, username,
				password, fileName, path);
	}

	/**
	 * 
	 * <p>
	 * Description:发送基本邮件
	 * </p>
	 * 
	 * @author yuboping
	 * @date 2016年7月8日 下午4:10:17
	 * @param to
	 * @param subject
	 * @param content
	 * @return
	 */
	public static boolean sendToc(String to, String subject, String content) {
		String smtp = "smtp.exmail.qq.com"; // SMTP
		String from = "yuboping";
		String username = "yuboping";
		String password = "Wht007521";
		return sendAndC(smtp, from, to, subject, content, username, password);
	}

}
