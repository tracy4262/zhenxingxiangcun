package com.ovit.nswy.member.util;

import com.ovit.nswy.member.model.Mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class JavaMailUtil {
	private static final String CHARSET = "UTF-8";

//	public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {
//		Properties props = new Properties();
//		// 开启debug调试
//		props.setProperty("mail.debug", "true");
//		// 发送服务器需要身份验证
//		props.setProperty("mail.smtp.auth", "false");
//		// 设置邮件服务器主机名
//		props.setProperty("mail.smtp.host", "mail.51bsi.com");
//		// 发送邮件协议名称
//		props.setProperty("mail.transport.protocol", "smtp");
//		// 设置环境信息
//		Session session = Session.getInstance(props);
//		// 创建邮件对象
//		Message msg = new MimeMessage(session);
//		msg.setSubject("加班通知");
//		// 设置邮件内容
//		msg.setText("周末加班，&nbsp;记得30，1号来公司");
//		/*
//		 * msg.setContent("<div>周末加班</div><div>记得29，30号来公司<br/><div>",
//		 * "text/html;charset=UTF-8");
//		 */
//		// 6. 设置发件时间
//		msg.setSentDate(new Date());
//		// 7. 保存设置
//		msg.saveChanges();
//		// 设置发件人
//		msg.setFrom(new InternetAddress("jzm@51bsi.com", "", "UTF-8"));
//		//
//		Address add = new InternetAddress("wangzhc@51bsi.com", "汪植成", "UTF-8");
//		Address add1 = new InternetAddress("yangxl@51bsi.com", "杨小林", "UTF-8");
//		msg.addRecipient(MimeMessage.RecipientType.TO, add);
//		msg.addRecipient(MimeMessage.RecipientType.BCC, add1);
//		Transport transport = session.getTransport();
//		// 连接邮件服务器
//		transport.connect();
//		// 发送邮件
//		transport.sendMessage(msg, msg.getAllRecipients());
//		// 关闭连接
//		transport.close();
//	}

	private static void sendMail(Mail mail) {
		Properties props = new Properties();
		// 开启debug调试
		props.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证
		props.setProperty("mail.smtp.auth", "false");
		// 设置邮件服务器主机名
		props.setProperty("mail.smtp.host", "mail.51bsi.com");
		// 发送邮件协议名称
		props.setProperty("mail.transport.protocol", "smtp");
		// 设置环境信息
		Session session = Session.getInstance(props);
		// 创建邮件对象
		Message msg = new MimeMessage(session);
		try {
			msg.setSubject(mail.getSubject());
			msg.setText(mail.getContent());
			// 6. 设置发件时间
			msg.setSentDate(new Date());
			// 7. 保存设置
			msg.saveChanges();
			// 设置发件人
			msg.setFrom(new InternetAddress(mail.getSender(), mail.getSenderName(), CHARSET));
			//
			String[] adds = mail.getRecipient();
			Address[] recipients = new Address[adds.length];
			int i = 0;
			for (String add : adds) {
				Address address = new InternetAddress(add, CHARSET);
				recipients[i++] = address;
			}
			msg.addRecipients(MimeMessage.RecipientType.TO, recipients);
			String[] ccs = mail.getRecipient();
			Address[] cc = new Address[adds.length];
			i = 0;
			for (String add : ccs) {
				Address address = new InternetAddress(add, CHARSET);
				cc[i++] = address;
			}
			msg.addRecipients(MimeMessage.RecipientType.CC, cc);
			Transport transport = session.getTransport();
			// 连接邮件服务器
			transport.connect();
			// 发送邮件
			transport.sendMessage(msg, msg.getAllRecipients());
			// 关闭连接
			transport.close();
		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}