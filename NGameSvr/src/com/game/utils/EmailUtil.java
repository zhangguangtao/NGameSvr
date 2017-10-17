package com.game.utils;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/** 
* @author 作者 E-mail: 1207834821@qq.com
* @version 创建时间：2017年9月27日 下午4:06:44 
* 类说明 
*/
public class EmailUtil {
	
	public static void sendMail(String receiveQQMail,String mailTitle ,Multipart mp) throws Exception{
		MimeMessage message  = getMimeMessage();
       // 设置收件人的邮箱
       InternetAddress to = new InternetAddress(receiveQQMail);
       message.setRecipient(RecipientType.TO, to);
       // 设置邮件标题
       message.setSubject(mailTitle);
       // 设置邮件的内容体
       //message.setContent("这是一封测试邮件http://www.baidu.com", "text/html;charset=UTF-8");
       message.setContent(mp);
       // 最后当然就是发送邮件啦
       Transport.send(message);
	}
	
	
	public static void sendMail(String receiveQQMail,String mailTitle ,String  text) throws Exception{
		MimeMessage message  = getMimeMessage();
       // 设置收件人的邮箱
       InternetAddress to = new InternetAddress(receiveQQMail);
       message.setRecipient(RecipientType.TO, to);
       // 设置邮件标题
       message.setSubject(mailTitle);
       // 设置邮件的内容体
       message.setContent(text, "text/html;charset=UTF-8");
       // 最后当然就是发送邮件啦
       Transport.send(message);
	}
	
	
	private static  MimeMessage getMimeMessage() throws Exception{
	   // 创建Properties 类用于记录邮箱的一些属性
       Properties props = new Properties();
       // 表示SMTP发送邮件，必须进行身份验证
       props.put("mail.smtp.auth", "true");
       //此处填写SMTP服务器
       props.put("mail.smtp.host", "smtp.qq.com");
       //端口号，QQ邮箱给出了两个端口，但是另一个我一直使用不了，所以就给出这一个587
       props.put("mail.smtp.port", "587");
       // 此处填写你的账号
       props.put("mail.user", "2206850048@qq.com");
       // 此处的密码就是前面说的16位STMP口令
       props.put("mail.password", "kpgsboouvjyfdice");
       // 构建授权信息，用于进行SMTP进行身份验证
       Authenticator authenticator = new Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication() {
               // 用户名、密码
               String userName = props.getProperty("mail.user");
               String password = props.getProperty("mail.password");
               return new PasswordAuthentication(userName, password);
           }
       };
       // 使用环境属性和授权信息，创建邮件会话
       Session mailSession = Session.getInstance(props, authenticator);
       // 创建邮件消息
       MimeMessage message = new MimeMessage(mailSession);
       // 设置发件人
       InternetAddress form = new InternetAddress(
               props.getProperty("mail.user"));
       message.setFrom(form);
       return message;
	}
	
	
	/**
	 * 邮件 内容
	 * @param text  
	 * @param affix  附件 文件
	 * @param affixName 发是附件
	 * @return
	 * @throws Exception
	 */
	public static  Multipart getContent(String text,String affix,String affixName)throws Exception{
		// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
       Multipart multipart = new MimeMultipart();
       // 设置邮件的文本内容
       BodyPart contentPart = new MimeBodyPart();
       contentPart.setText(text);
       multipart.addBodyPart(contentPart);
       // 添加附件
       BodyPart messageBodyPart = new MimeBodyPart();
       DataSource source = new FileDataSource(affix);
       // 添加附件的内容
       messageBodyPart.setDataHandler(new DataHandler(source));
       // 添加附件的标题
       // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
       sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
       messageBodyPart.setFileName("=?GBK?B?"
               + enc.encode(affixName.getBytes()) + "?=");
       multipart.addBodyPart(messageBodyPart);
       return multipart;
	}

}
