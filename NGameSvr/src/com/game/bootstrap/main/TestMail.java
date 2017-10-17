package com.game.bootstrap.main;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.game.utils.EmailUtil;

/** 
* @author 作者 E-mail: 1207834821@qq.com
* @version 创建时间：2017年9月27日 下午2:42:20 
* 类说明  测试 发送邮件
*/
public class TestMail {
	
	private String host = ""; // smtp服务器
    private String from = ""; // 发件人地址
    private String to = ""; // 收件人地址
    private String affix = ""; // 附件地址
    private String affixName = ""; // 附件名称
    private String user = ""; // 用户名
    private String pwd = ""; // 密码
    private String subject = ""; // 邮件标题

    public void setAddress(String from, String to, String subject) {
        this.from = from;
        this.to = to;
        this.subject = subject;
    }

    public void setAffix(String affix, String affixName) {
        this.affix = affix;
        this.affixName = affixName;
    }

    public void send(String host, String user, String pwd) {
        this.host = host;
        this.user = user;
        this.pwd = pwd;

        Properties props = new Properties();

        // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.host", host);
        // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        props.put("mail.smtp.auth", "true");

        // 用刚刚设置好的props对象构建一个session
        Session session = Session.getDefaultInstance(props);

        // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
        // 用（你可以在控制台（console)上看到发送邮件的过程）
        session.setDebug(true);

        // 用session为参数定义消息对象
        MimeMessage message = new MimeMessage(session);
        try {
            // 加载发件人地址
            message.setFrom(new InternetAddress(from));
            // 加载收件人地址
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // 加载标题
            message.setSubject(subject);

            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText("邮件的具体内容在此");
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

            // 将multipart对象放到message中
            message.setContent(multipart);
            // 保存邮件
            message.saveChanges();
            // 发送邮件
            Transport transport = session.getTransport("smtp");
            // 连接服务器的邮箱
            transport.connect(host, user, pwd);
            // 把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) throws Exception{
		
//		TestMail cn = new TestMail();
//	        // 设置发件人地址、收件人地址和邮件标题
//	        cn.setAddress("m157****0661@163.com", "80818***@qq.com", "一个带附件的JavaMail邮件");
//	        // 设置要发送附件的位置和标题
//	        cn.setAffix("f:/123.txt", "123.txt");
//	        
//	        /**
//	         * 设置smtp服务器以及邮箱的帐号和密码
//	         * 用QQ 邮箱作为发生者不好使 （原因不明）
//	         * 163 邮箱可以，但是必须开启  POP3/SMTP服务 和 IMAP/SMTP服务
//	         * 因为程序属于第三方登录，所以登录密码必须使用163的授权码  
//	         */
//        // 注意： [授权码和你平时登录的密码是不一样的]
//	        cn.send("smtp.163.com", "m157****0661@163.com", "gebilaowang123");
		
		
		/*
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

        // 设置收件人的邮箱
        InternetAddress to = new InternetAddress("1207834821@qq.com");
        message.setRecipient(RecipientType.TO, to);

        // 设置邮件标题
        message.setSubject("测试邮件");

        // 设置邮件的内容体
        //message.setContent("这是一封测试邮件http://www.baidu.com", "text/html;charset=UTF-8");
        
        Multipart mp = getContent("邮件内容","f:/123.txt", "123.apk");
        
        message.setContent(mp);

        // 最后当然就是发送邮件啦
        Transport.send(message);
        */
        
		for (int i = 0; i < 100; i++) {
			String receiveQQMail = "120783482"+i+"@qq.com";
	        String mailTitle = "hello word!"+i;
			try {
		        //Multipart mp2 = getContent("欢迎欢迎"+i,"f:/123.txt", i+"123.apk");
		        
		        //EmailUtil.sendMail(receiveQQMail, mailTitle, mp2);
		        EmailUtil.sendMail(receiveQQMail, mailTitle, "http://www.baidu.com");
		        System.out.println("i == "+i);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(receiveQQMail);
			}
			
	        
	        Thread.sleep(20000);
	       
		}
        
		
		
		
		
		
	}
	
	public static void sendMail(String receiveQQMail,String mailTitle ,Multipart mp) throws Exception{
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
