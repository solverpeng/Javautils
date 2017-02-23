package com.solverpeng.common.entity;

import java.util.Properties;

/**
 * <pre>
 *      author: solverpeng
 *      blog  : http://solverpeng.com
 *      time  : 2017/2/23
 *      desc  : 邮件基本信息实体
 * </pre>
 */
public class MailSenderInfo {
    /**
     * 发送邮件的服务器的IP和端口
     */
    private String mailServerHost;
    /**
     * 发送邮件的服务器的端口
     */
    private String mailServerPort = "25";
    /**
     * 邮件发送者的地址
     */
    private String fromAddress;
    /**
     * 邮件接收者的地址
     */
    private String toAddress;
    /**
     * 登陆邮件发送服务器的用户名
     */
    private String userName;
    /**
     * 登陆邮件发送服务器的密码
     */
    private String password;
    /**
     * 是否需要身份验证
     */
    private boolean validate = false;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件的文本内容
     */
    private String content;
    /**
     * 邮件附件的文件名
     */
    private String[] attachFileNames;
    private boolean isSsl = false;

    public boolean isSsl() {
        return isSsl;
    }

    public void setSsl(boolean isSsl) {
        this.isSsl = isSsl;
    }

    /**
     * 获得邮件会话属性
     */
    public Properties getProperties() {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties p = new Properties();
        if (this.isSsl) {
            p.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            p.setProperty("mail.smtp.socketFactory.fallback", "false");
            p.setProperty("mail.smtp.socketFactory.port", this.mailServerPort);
        }
        p.put("mail.smtp.host", this.mailServerHost);
        p.put("mail.smtp.port", this.mailServerPort);
        p.put("mail.smtp.auth", validate ? "true" : "false");
        return p;
    }

    public String getMailServerHost() {
        return mailServerHost;
    }

    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }

    public String getMailServerPort() {
        return mailServerPort;
    }

    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public String[] getAttachFileNames() {
        return attachFileNames;
    }

    public void setAttachFileNames(String[] fileNames) {
        this.attachFileNames = fileNames;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String textContent) {
        this.content = textContent;
    }
}   